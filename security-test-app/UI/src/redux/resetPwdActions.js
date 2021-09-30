import {RESETPWD_FAILURE,RESETPWD_LEGIT} from './types';

export const resetPasswordLegit= (content)=>{
    localStorage.setItem('RESET_TOKEN',content);
    return {
        type:RESETPWD_LEGIT,
        payload:content
    }
}

export const resetPasswordFailure = (error)=>{
    localStorage.setItem('RESET_TOKEN', null);
    return {
        type:RESETPWD_FAILURE,
        payload:error
    }
}