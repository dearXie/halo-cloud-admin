import httpRequest from '@/libs/HttpRequest';

/**
 * 获取实例
 * @param id
 * @returns {*}
 */
export const getInstance = id =>{
    return httpRequest.request({
        method: 'get',
        url: `/getInstance/${id}`
    });
}