import React, { useState } from "react";
import * as FaIcons from "react-icons/fa";
import * as AiIcons from "react-icons/ai";
//import * as ImIcons from "react-icons/im";
import { Link } from "react-router-dom";
import { SidebarData } from "./SidebarData";
import LogoutService from "../service/login-service";
import sidebarStyle from "./style/sidebar.module.css";
import { IconContext } from "react-icons";
import { Typography } from "@material-ui/core";

function Navbar(props) {
  const [sidebar, setSidebar] = useState(false);

  const showSidebar = () => setSidebar(!sidebar);

  const handleLogOut = () => {
    localStorage.removeItem("username");

    LogoutService.logout()
      .then((response) => {
        props.history.push("/login");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  function isLogout(props) {
    let logout;
    if (props === "Odjava") {
      logout = (
        <span className={sidebarStyle.span_class} onClick={handleLogOut}>
          {props}
        </span>
      );
    } else {
      logout = <span className={sidebarStyle.span_class}>{props}</span>;
    }
    return logout;
  }

  return (
    <>
      <IconContext.Provider value={{ color: "#000", size: "1em" }}>
        <div className={sidebarStyle.navbar}>
          <Link to="#" className={sidebarStyle.menu_bars}>
            <FaIcons.FaBars onClick={showSidebar} />
          </Link>

          {/* <h1 className={sidebarStyle.title}> 
            <IconContext.Provider value={{ color: '#cc0000' }}>
              <ImIcons.ImHeart></ImIcons.ImHeart>
            </IconContext.Provider>
            &nbsp; Pomozi mi &nbsp;
          </h1> */}

          <a href="/home" style={{ textDecoration: "none" }}>
            <Typography
              variant="h4"
              color="textSecondary"
              align="left"
              display="inline"
            >
              Pomozi
            </Typography>
            <Typography variant="h4" color="secondary" display="inline">
              Mi
            </Typography>
          </a>
        </div>
        <nav
          className={
            sidebar
              ? `${sidebarStyle.nav_menu} ${sidebarStyle.active}`
              : sidebarStyle.nav_menu
          }
        >
          <span className={sidebarStyle.nav_menu_items} onClick={showSidebar}>
            <li className={sidebarStyle.navbar_toggle}>
              <Link to="#" className={sidebarStyle.menu_bars}>
                <AiIcons.AiOutlineLeft />
              </Link>
            </li>
            {SidebarData.map((item, index) => {
              let logout = isLogout(item.title);
              return (
                <li key={index} className={sidebarStyle.nav_text}>
                  <Link to={item.path}>
                    {item.icon}
                    {logout}
                  </Link>
                </li>
              );
            })}
          </span>
        </nav>
      </IconContext.Provider>
    </>
  );
}

export default Navbar;
