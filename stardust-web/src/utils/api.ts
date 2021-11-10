import axios from "axios";
axios.defaults.timeout = 5000;
axios.defaults.withCredentials = true;

const preUrl: string = import.meta.env.VITE_BASE_URL;

enum METHOD{
  GET = 'get',
  POST = 'post',
  DELETE = 'delete',
  PUT = 'put'
}

async function request(url: string, method: METHOD, params?: any, config?: any): Promise<any> {
  return axios({
    url: preUrl + url,
    method: method,
    params: params,
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Headers":
        "Content-Type, Content-Length, Authorization, Accept, X-Requested-With",
      "Content-Type": "application/json;charset=utf-8",
    },
  });
}

export {
  METHOD,
  request
}
