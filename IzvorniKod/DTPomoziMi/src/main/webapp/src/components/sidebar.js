import React, { useState } from 'react';
import * as FaIcons from 'react-icons/fa';
import * as AiIcons from 'react-icons/ai';
import * as ImIcons from "react-icons/im";
import { Link } from 'react-router-dom';
import { SidebarData } from './SidebarData';
import LogoutService from "../service/login-service";
import './style/sidebar.css';
import { IconContext } from 'react-icons';

function Navbar(props) {
  const [sidebar, setSidebar] = useState(false);

  const showSidebar = () => setSidebar(!sidebar);

  const handleLogOut = () => {
    localStorage.removeItem("username");
    
    LogoutService.logout().then((response) => {
        props.history.push('/login');
    }).catch((error) => {
        console.log(error);
    });
  }

function isLogout(props){
  let logout;
  if(props=="Odjava"){
    logout=<span onClick={handleLogOut}>{props}</span>
  }else{
    logout=<span>{props}</span>
  }
  return logout
}

  return (
    <>
      <IconContext.Provider value={{ color: '#000', size: "1.5em"}}>
        <div className='navbar'>
          <Link to='#' className='menu-bars'>
            <FaIcons.FaBars onClick={showSidebar} />
          </Link>
          <h1 className="title"> Pomozi mi &nbsp;
            <IconContext.Provider value={{ color: '#cc0000' }}>
              <ImIcons.ImHeart></ImIcons.ImHeart>
            </IconContext.Provider>
          </h1>
          
        </div>
        <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
          <ul className='nav-menu-items' onClick={showSidebar}>
            <li className='navbar-toggle'>
              <Link to='#' className='menu-bars'>
                <AiIcons.AiOutlineLeft/>
              </Link>
            </li>
            {SidebarData.map((item, index) => {
              let logout=isLogout(item.title);
              return (
                <li key={index} className={item.cName}>
                  <Link to={item.path}>
                    {item.icon }
                    {logout}
                  </Link>
                </li>
              );
            })}
          </ul>
        </nav>
      </IconContext.Provider>
    </>
  );
}

export default Navbar;

