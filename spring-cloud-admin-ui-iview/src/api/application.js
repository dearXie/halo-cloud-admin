import httpRequest from '@/libs/HttpRequest';

export const getApplications = ()=> {
    return httpRequest.request({
        method: 'get',
        url: '/applications'
    });
};

export const getApplicationPage = (currentPage,pageSize)=> {
    return httpRequest.request({
        method: 'get',
        url: `/application/${pageSize}/${currentPage}`
    });
};

/**
 * 获取某个应用的信息
 * @param name
 * @returns {*}
 */
export const getApplicationInfo = (name)=>{
    return httpRequest.request({
        method: 'get',
        url: `/applications/${name}`
    });
}