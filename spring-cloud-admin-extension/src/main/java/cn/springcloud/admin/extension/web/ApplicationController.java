package cn.springcloud.admin.extension.web;

import cn.springcloud.admin.common.ResultData;
import cn.springcloud.admin.common.domain.PageResult;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.values.BuildVersion;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import de.codecentric.boot.admin.server.web.ApplicationsController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static de.codecentric.boot.admin.server.domain.values.StatusInfo.STATUS_UNKNOWN;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;


@RestController
public class ApplicationController {

    private final InstanceRegistry registry;

    public ApplicationController(InstanceRegistry registry){
        this.registry = registry;
    }

    @GetMapping(path = "/application/{size}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultData applications(@PathVariable Integer size,@PathVariable Integer page){
        List<ApplicationsController.Application> applications = registry.getInstances()
                .filter(Instance::isRegistered)
                .groupBy(instance -> instance.getRegistration().getName())
                .flatMap(grouped -> toApplication(grouped.key(), grouped)).collectList().block();
        Integer total = applications.size();
        Integer start = (page - 1) * size;
        Integer end = page * size;
        if(end > total) {
        	end = total;
        }
        PageResult<ApplicationsController.Application> pageResult = PageResult.<ApplicationsController.Application>builder()
        		.currentPage(page)
        		.total(total).totalPage(PageResult.getTotalPage(total, size)).data(applications.subList(start,end)).build();
        return ResultData.builder().data(pageResult).build();
    }

    protected Mono<ApplicationsController.Application> toApplication(String name, Flux<Instance> instances) {
        return instances.collectList().map(instanceList -> {
            ApplicationsController.Application group = new ApplicationsController.Application(name);
            group.setInstances(instanceList);
            group.setBuildVersion(getBuildVersion(instanceList));
            Tuple2<String, Instant> status = getStatus(instanceList);
            group.setStatus(status.getT1());
            group.setStatusTimestamp(status.getT2());
            return group;
        });
    }

    @Nullable
    protected BuildVersion getBuildVersion(List<Instance> instances) {
        List<BuildVersion> versions = instances.stream()
                .map(Instance::getBuildVersion)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .collect(toList());
        if (versions.isEmpty()) {
            return null;
        } else if (versions.size() == 1) {
            return versions.get(0);
        } else {
            return BuildVersion.valueOf(versions.get(0) + " ... " + versions.get(versions.size() - 1));
        }
    }

    protected Tuple2<String, Instant> getStatus(List<Instance> instances) {
        //TODO: Correct is just a second readmodel for groups
        Map<String, Instant> statusWithTime = instances.stream()
                .collect(toMap(instance -> instance.getStatusInfo().getStatus(),
                        Instance::getStatusTimestamp,
                        this::getMax
                ));
        if (statusWithTime.size() == 1) {
            Map.Entry<String, Instant> e = statusWithTime.entrySet().iterator().next();
            return Tuples.of(e.getKey(), e.getValue());
        }

        if (statusWithTime.containsKey(StatusInfo.STATUS_UP)) {
            Instant oldestNonUp = statusWithTime.entrySet()
                    .stream()
                    .filter(e -> !StatusInfo.STATUS_UP.equals(e.getKey()))
                    .map(Map.Entry::getValue)
                    .min(naturalOrder())
                    .orElse(Instant.EPOCH);
            Instant latest = getMax(oldestNonUp, statusWithTime.getOrDefault(StatusInfo.STATUS_UP, Instant.EPOCH));
            return Tuples.of(StatusInfo.STATUS_RESTRICTED, latest);
        }

        return statusWithTime.entrySet()
                .stream()
                .min(Map.Entry.comparingByKey(StatusInfo.severity()))
                .map(e -> Tuples.of(e.getKey(), e.getValue()))
                .orElse(Tuples.of(STATUS_UNKNOWN, Instant.EPOCH));
    }

    private Instant getMax(Instant t1, Instant t2) {
        return t1.compareTo(t2) >= 0 ? t1 : t2;
    }
}
