import React, { useState, useEffect } from "react";
import * as FaIcons from "react-icons/fa";
import * as AiIcons from "react-icons/ai";
//import * as ImIcons from "react-icons/im";
import { Link } from "react-router-dom";
import { withStyles } from '@material-ui/core/styles';
import { SidebarData } from "./SidebarData";
import LogoutService from "../service/login-service";
import sidebarStyle from "./style/sidebar.module.css";
import { IconContext } from "react-icons";
import { Typography } from "@material-ui/core";
import UserService from '../service/user-service';
import NotificationsIcon from '@material-ui/icons/Notifications';
import Badge from '@material-ui/core/Badge';
import IconButton from '@material-ui/core/IconButton';
import Button from '@material-ui/core/Button';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import InboxIcon from '@material-ui/icons/MoveToInbox';
import DraftsIcon from '@material-ui/icons/Drafts';
import SendIcon from '@material-ui/icons/Send';


const StyledMenu = withStyles({
  paper: {
    border: '1px solid #d3d4d5',
  },
})((props) => (
  <Menu
    elevation={0}
    getContentAnchorEl={null}
    anchorOrigin={{
      vertical: 'bottom',
      horizontal: 'center',
    }}
    transformOrigin={{
      vertical: 'top',
      horizontal: 'center',
    }}
    {...props}
  />
));

const StyledMenuItem = withStyles((theme) => ({
  root: {
    '&:focus': {
      backgroundColor: theme.palette.primary.main,
      '& .MuiListItemIcon-root, & .MuiListItemText-primary': {
        color: theme.palette.common.white,
      },
    },
  },
}))(MenuItem);

const ITEM_HEIGHT = 48;

function Navbar(props) {




  const [sidebar, setSidebar] = useState(false);
  const [value, setValue] = useState(0);
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [notifs, setNotifs] = useState([]);
  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
    let count = 0;
    for(let notif of notifs){
      count++;
      UserService.setReadNotifs(notif.idNotification);
    }
    setValue(0);
  };

  const showSidebar = () => setSidebar(!sidebar);

  useEffect(() => {
    const userId = UserService.getUserContext().id;
    UserService.getNotifications(userId)
        .then((response) => {
          let count = 0;
          if(response.data._embedded !== undefined){
          console.log(response.data._embedded.notifications);
          setNotifs(response.data._embedded.notifications);
          console.log(response.data._embedded.notifications.length);
          for(let notif of response.data._embedded.notifications){
            if(notif.recived === false){
              count++;
            }
          }

          setValue(count);
          console.log(response);
          }
        })
        .catch((error) => {
            alert(error);
        })
}, []);

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

  function isLogout(prop) {
    let logout;
    
    if (props === "Odjava") {
      logout = (
        <span className={sidebarStyle.span_class} onClick={handleLogOut}>
          {prop}
        </span>
      );
    } else {
      logout = <span className={sidebarStyle.span_class}>{prop}</span>;
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


          <IconButton aria-label="show 17 new notifications" color="inherit" onClick={handleClick}>
              <Badge badgeContent={value} color="secondary">
                <NotificationsIcon />
              </Badge>
            </IconButton>
            <Menu
        id="long-menu"
        anchorEl={anchorEl}
        keepMounted
        open={open}
        onClose={handleClose}
        PaperProps={{
          style: {
            maxHeight: ITEM_HEIGHT * 10,
            width: '70ch',
            whiteSpace: "break-spaces",
          },
        }}
      >
        {notifs.map((notif) => (
          <>
          <MenuItem key={notif.idNotification} style= {{
            whiteSpace: "break-spaces",
          }} onClick={handleClose}>
            {notif.message}
          </MenuItem>
          </>
        ))}
      </Menu>
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
                <>
                
                <li key={index} className={sidebarStyle.nav_text}>
                  <Link to={item.title === "Profil" ? item.path + "/" + UserService.getUserContext().id : item.path}>
                    {item.icon}
                    {logout}
                  </Link>
                </li>
                </>
              );
            })}
          </span>
        </nav>
      </IconContext.Provider>
    </>
  );
}

export default Navbar;
