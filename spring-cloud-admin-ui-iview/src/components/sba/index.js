const components = [];

/* global require */
const context = require.context('.', false, /^(?:(?!.*\.spec\.(js|vue)$).)*\.(js|vue)$/);
context.keys().forEach(function (key) {
    const name = /^(.\/)+(.*)\.(vue|js)$/.exec(key)[2];
    components.push({name, component: context(key).default})
});

export default {
    install(Vue) {
        components.forEach(component => Vue.component(component.name, component.component));
    }
}