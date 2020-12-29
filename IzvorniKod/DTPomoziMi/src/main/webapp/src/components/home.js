import React from 'react'
import homeStyle from './style/home.module.css'
import LogoutService from "../service/login-service";
import Sidebar from './sidebar';
import Slideshow from './slider';
import Home from './home';
import {BrowserRouter as Router, Route, Switch } from 'react-router-dom';


export default function home(props) {
    let name = localStorage.getItem("username");
    //document.body.style = 'background-image: none;';
 
 

    return (
        <>
        <Sidebar /> 
        <img src="../images/holding-hands.png" alt=""/>
        <div className = {homeStyle.container}>
        <div className={homeStyle.pomozi}>Pomozi mi</div>
        <div className={homeStyle.msg}>Malo djelo velikog<span className={homeStyle.znacaja}> značaja</span>.
        </div> 
        </div>  
        <Slideshow/> 
        </>
    )
}