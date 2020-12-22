import React from 'react'
import './style/home.css'
import { Button } from 'react-bootstrap';
import LogoutService from "../service/login-service";

export default function home(props) {
    let name = localStorage.getItem("username");
 

 
    const handleLogOut = () => {
        localStorage.removeItem("username");
        
        LogoutService.logout().then((response) => {
            props.history.push('/login');
        }).catch((error) => {
            console.log(error);
        });
    }


    return (
        <>
        <div className = "container">
        <div className="msg">korisnik {name} je uspjesno ulogiran
        <Button variant="secondary" onClick={handleLogOut}>Logout</Button>
        </div>
        </div>
        </>
    )
}