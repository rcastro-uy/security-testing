import axios from 'axios';

const getToken=()=>{
    return localStorage.getItem('USER_KEY');
}

export const recoverPassword=(recoverRequest)=>{
    return axios({
        'method':'POST',
        'url':`${process.env.hostUrl||'http://localhost:8080'}/api/v1/users/passRecover`,
        'data':recoverRequest,
    })
}

export const changePassword=(recoverRequest)=>{
    return axios({
        'method':'POST',
        'url':`${process.env.hostUrl||'http://localhost:8080'}/api/v1/users/passChange`,
        'data':recoverRequest,
    })
}