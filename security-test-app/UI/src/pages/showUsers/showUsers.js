import React, {useState} from "react";
import {fetchUsers} from '../../api/authenticationService';

//quiero mostrar los usuarios de la aplicacion, tomar dashboard.js como ejemplo
//discriminar entre ADMIN y USER
export const ShowUsers = (props) => {

    const [data,setData]=useState();

    React.useEffect(()=>{
        fetchUsers().then((response)=>{
            console.log(response);
            setData(response.data);
        }).catch((e)=>{
            localStorage.clear();
            props.history.push('/');
        })
    },[])

    return (
        
        <div>
            {data && <h1> Anda la autenticacion {`${data.firstName} ${data.lastName}`} </h1>}
        </div>
    )
}