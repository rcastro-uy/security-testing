import React, {useState} from "react";
import {fetchUsers} from '../../api/authenticationService';

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


{/*podria haber hecho una lista pero no tenia ganas XD*/}
    return (
        <div>
            {data != null ? data.map((dato) => (
                <h3 key={`${dato.id}`}> 
                    usuario con id: {`${dato.id}`}
                    {<br></br>}
                    {<br></br>} 
                    nombre : {`${dato.firstName}`}
                    {<br></br>}
                    apellido : {`${dato.lastName}`} 
                    {<br></br>}
                    rol : {`${dato.authorities.map((rol) => (rol.authority))}`}
                    {<br></br>}
                    {<br></br>}
                    {<br></br>}
                </h3>
            )) : null}
        </div>
    )
}