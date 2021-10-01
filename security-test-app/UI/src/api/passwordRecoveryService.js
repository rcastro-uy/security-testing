import axios from 'axios';

const getToken=()=>{
    return localStorage.getItem('RESET_TOKEN');
}

export const recoverPassword=(recoverRequest)=>{
    return axios({
        'method':'POST',
        'url':`${process.env.hostUrl||'http://localhost:8080'}/api/v1/users/passRecover`,
        'data':{email:recoverRequest},
    })
}

export const changePassword=(recoverRequest)=>{
    console.log(recoverRequest);
    return axios({
        'method':'POST',
        'url':`${process.env.hostUrl||'http://localhost:8080'}/api/v1/users/passChange`,
        'data':recoverRequest
    })
}

export const checkToken=(recoverRequest)=>{
    return axios({
        'method':'POST',
        'url':`${process.env.hostUrl||'http://localhost:8080'}/api/v1/users/check`,
        'data':recoverRequest
    })
}