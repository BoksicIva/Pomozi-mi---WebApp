import React from 'react'
import './home.css'
import { Button } from 'react-bootstrap';
import LogoutService from "../service/login-service";

export default function home(props) {
    let name = localStorage.getItem("username");
 
    LogoutService.notPer().then((response) => {
    }).catch((error) => {
        props.history.push('/login');
        console.log(error);
    });
 
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