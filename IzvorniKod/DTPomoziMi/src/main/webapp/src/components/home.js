import React from 'react'
import homeStyle from './style/home.module.css'

import Sidebar from "./sidebar";
import Slideshow from "./slider";


export default function home(props) {
    let name = localStorage.getItem("username");
    
 
 

    return (
    <div className={homeStyle.background}>
        <Sidebar /> 
        <div className = {homeStyle.container}>
            <div className={homeStyle.pomozi}>Pomozi mi</div>
            <div className={homeStyle.msg}>Malo djelo velikog<span className={homeStyle.znacaja}> značaja </span>.
            </div> 
            <br></br>
            <Slideshow/> 
        </div>  
    </div>    
    )
}
