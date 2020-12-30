import React from 'react'
import './style/home.css'

import Sidebar from './sidebar';
import Slideshow from './slider';




export default function home(props) {
    let name = localStorage.getItem("username");
 
 

    return (
        <>
        <Sidebar /> 
        <img src="../images/holding-hands.png" alt=""/>
        <div className = "container">
        <div className="pomozi">Pomozi mi</div>
        <div className="msg">Malo djelo velikog<span className="znacaja"> značaja</span>.
        </div> 
        </div>  
        <Slideshow/>
        </>
    )
}