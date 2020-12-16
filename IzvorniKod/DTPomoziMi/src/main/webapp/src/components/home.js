import React from 'react'
import './home.css'
import { Button } from 'react-bootstrap';
import LogoutService from "../service/login-service";

export default function home(props) {
    let name = localStorage.getItem("username");
<<<<<<< HEAD
/* 
=======

>>>>>>> bdfc6bbc144e41dccf30bec1db709c753d7ea87f
    LogoutService.notPer().then((response) => {
    }).catch((error) => {
        props.history.push('/login');
        console.log(error);
    });
<<<<<<< HEAD
 */
    const handleLogOut = () => {
        localStorage.removeItem("username");
        
=======

    const handleLogOut = () => {
        localStorage.removeItem("username");

>>>>>>> bdfc6bbc144e41dccf30bec1db709c753d7ea87f
        LogoutService.logout().then((response) => {
            props.history.push('/login');
        }).catch((error) => {
            console.log(error);
        });
    }


    return (
        <>
<<<<<<< HEAD
        <div className = "container">
        <div className="msg">korisnik {name} je uspjesno ulogiran
        <Button variant="secondary" onClick={handleLogOut}>Logout</Button>
        </div>
        </div>
=======
            <div className = "container">
                <div className="msg">korisnik {name} je uspjesno ulogiran
                    <Button variant="secondary" onClick={handleLogOut}>Logout</Button>
                </div>
            </div>
>>>>>>> bdfc6bbc144e41dccf30bec1db709c753d7ea87f
        </>
    )
}