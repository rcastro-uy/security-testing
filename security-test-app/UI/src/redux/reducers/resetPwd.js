import {RESETPWD_FAILURE,RESETPWD_LEGIT} from '../types';

const getToken=()=>{
    return localStorage.getItem('RESET_TOKEN');
}

const initialState={
    error:'',
    token:getToken()
};

const resetPwd=(state=initialState,action)=>{
    console.log("Reducer resetPwd");
    switch(action.type){
        case RESETPWD_LEGIT:
            const data = action.payload
            return {...state,error:'',token:data};

        case RESETPWD_FAILURE:
            const error=action.payload;
            return {...state,error:error, token:''};

        default:
            return state;
    }
}


export default resetPwd;