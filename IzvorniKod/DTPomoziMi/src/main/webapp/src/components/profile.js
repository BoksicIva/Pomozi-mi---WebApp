import React, { useState, useEffect } from "react";
import { makeStyles, useTheme } from "@material-ui/core/styles";
import {
  Container,
  Typography,
  Avatar,
  List,
  ListItem,
  ListItemText,
  ListItemAvatar,
  Divider,
  Paper,
  IconButton,
  TextField,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Button,
  Tabs,
  Tab,
  Box,
} from "@material-ui/core";
import VisibilityIcon from "@material-ui/icons/Visibility";
import VisibilityOffIcon from "@material-ui/icons/VisibilityOff";
import AccountCircleIcon from "@material-ui/icons/AccountCircle";
import "fontsource-roboto";
import PropTypes from "prop-types";
import SwipeableViews from "react-swipeable-views";
import UserService from "../service/user-service";
import Sidebar from "./sidebar";
import ReportIcon from '@material-ui/icons/Report';
import ReportOffIcon from '@material-ui/icons/ReportOff';

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <Typography
      component="div"
      role="tabpanel"
      hidden={value !== index}
      id={`full-width-tabpanel-${index}`}
      aria-labelledby={`full-width-tab-${index}`}
      {...other}
    >
      <Box>{children}</Box>
    </Typography>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};

function a11yProps(index) {
  return {
    id: `full-width-tab-${index}`,
    "aria-controls": `full-width-tabpanel-${index}`,
  };
}

const useStyles = makeStyles((theme) => ({
  profileInfo: {
    height: 300,
    width: "100%",
    padding: 20,
    display: "flex",
    alignItems: "center",
    //backgroundColor: "lightgray",
    boxShadow: "0 0 10px 0 rgba(0, 0, 0, 0.2)",
  },

  avatarButton: {
    minWidth: 120,
    minHeight: 120,
    maxHeight: 250,
    maxWidth: 250,
    width: "17vw",
    height: "17vw",
    padding: 0,
    boxShadow: "0 0 10px 0 rgba(0, 0, 0, 0.2)",
  },

  avatar: {
    width: "100%",
    height: "100%",
  },

  about: {
    //backgroundColor: "lightgray",
    height: "100%",
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    padding: "5%",
  },

  visibilityButton: {
    width: 40,
    height: 40,
    boxShadow: "0 0 10px 0 rgba(0, 0, 0, 0.2)",
  },

  visibilityLabel: {
    margin: 0,
  },

  visibilityRoot: {
    marginLeft: 20,
  },

  body: {
    paddingLeft: 20,
    paddingRight: 20,
  },

  requestsContainer: {
    display: "flex",
    alignItems: "center",
    //backgroundColor: "lightgray",
    marginTop: 20,
    justifyContent: "center",
  },

  list: {
    width: "100%",
  },

  avatarLabel: {
    margin: 0,
    height: "100%",
  },

  tabs: {
    flexGrow: 1,
    boxShadow: "0 0 10px 0 rgba(0, 0, 0, 0.2)",
  },

  tabWrapper: {
    margin: 0,
  },

  scrollIndicator: {
    margin: 0,
  },
}));

const Profile = (props) => {
  const classes = useStyles();
  const theme = useTheme();
  document.body.style = "background-image: none; background-color: white";
  const [open, setOpen] = useState(false);
  const [about, setAbout] = useState(true);
  const [value, setValue] = useState(0);
  const [isLoading, setLoading] = useState(true);
  const [userData, setUserData] = useState({});
  const [userStatistics, setUserStatistics] = useState({});
  const [requests, setRequests] = useState({});
  const [location, setLocation] = useState({});
  const [isUser, setUser] = useState(false);
  const [isAdmin, setAdmin] = useState(false);
  const [isBlocked, setBlocked] = useState(false);


  function handleChange(event, newValue) {
    setValue(newValue);
  }

  function handleChangeIndex(index) {
    setValue(index);
  }

  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleAbout = () => {
    setAbout(!about);
  };
  const handleBlock = value => () => {
    UserService.blockUser(props.match.params.id, value).then((response) => {
      props.history.push("/list");;
    })
      .catch((error) => {
        alert(error);
      })
  }


  useEffect(() => {
    //console.log(props.match.params.id);
    const user = UserService.getUserContext();
    UserService.getUser(props.match.params.id).then((response) => {
      console.log(response);
      setUserData(response.data);
      if (response.data.enabled === false) {
        setBlocked(true);
      }
      console.log(user.id == props.match.params.id);
      if (user.id == props.match.params.id) {
        setUser(true);
      }
      for (let role of user.roles) {
        if (role === "ROLE_ADMIN") {
          setAdmin(true);
        }
      }
    });
    UserService.getUserStatistics(props.match.params.id).then((response) => {
      setUserStatistics(response.data);

    });
    if (props.match.params.id == user.id) {
      UserService.getAuthored(user.id).then((response) => {
        setRequests(response.data);
        setLoading(false);
      });
    } else {
      setRequests();
      setLoading(false);
    }
  }, [props.match.params.id]);


  const mapRequests = (request) => {

    return (
      <>
        <ListItem alignItems="flex-start">
          <ListItemAvatar>
            <Avatar>{request.author.firstName.substring(0, 1)}</Avatar>
          </ListItemAvatar>
          <ListItemText
            primary={request.author.email}
            secondary={
              <React.Fragment>
                <Typography
                  component="span"
                  variant="body2"
                  color="textPrimary"
                >
                  {request.author.firstName + " " + request.author.lastName}
                </Typography>
                {" — " + request.description}
              </React.Fragment>
            }
          />
        </ListItem>
        <Divider variant="inset" component="li" />
      </>
    );
  };


  if (isLoading) {
    return null;
  } else {
    return (
      <div>
        <Sidebar />
        <div className={classes.body}>
          <Container className={classes.profileInfo} maxWidth="lg">
            <IconButton
              className={classes.avatarButton}
              onClick={handleClick}
              classes={{ label: classes.avatarLabel }}
            >
              {localStorage.getItem("photo") ? (
                <Avatar
                  alt="avatar"
                  src={localStorage.getItem("photo")}
                  className={classes.avatar}
                />
              ) : (
                  <AccountCircleIcon className={classes.avatar} />
                )}
            </IconButton>

            <Container
              className={classes.about}
              maxWidth={false}
              disableGutters={true}
            >
              <div style={{ display: "flex", alignItems: "center" }}>
                <Typography variant="h4" color="textSecondary" component="span">
                  {userData.firstName} {userData.lastName}
                </Typography>
                <IconButton
                  classes={{
                    label: classes.visibilityLabel,
                    root: classes.visibilityRoot,
                  }}
                  onClick={handleAbout}
                  className={classes.visibilityButton}
                >
                  {about ? <VisibilityOffIcon /> : <VisibilityIcon />}
                </IconButton>
              </div>

              <Typography
                variant="h5"
                color="textSecondary"
                style={{ display: about ? "block" : "none" }}
              >

                {isUser ? <p>
                  Država : {location.state}
                  < br />
                Mjesto: {location.town}
                  <br />
                Adresa: {location.adress}
                  <br />
                </p>
                  : null
                }
                <div>
                  <a
                    href={"mailto: " + localStorage.getItem("email")}
                    style={{ textDecoration: "none", color: "#3f51b5" }}
                  >
                    <Typography variant="h5">{userData.email}</Typography>
                  </a>
                </div>
              </Typography>

              <Typography
                variant="h5"
                color="textSecondary"
                style={{ display: about ? "none" : "block" }}
              >
                Ocjena: {userStatistics.avgGrade}
                <br />
                Izvršeni zahtjevi: {userStatistics.numFinalizedR}
                <br />
                Zadani zahtjevi: {userStatistics.numAuthoredR}
                <br />
                Rang: {userStatistics.rank}
              </Typography>
            </Container>
            {isUser ? null :
            isAdmin ?
              isBlocked ? <IconButton aria-label="block"
                title="Blokiraj korisnika"
                onClick={handleBlock("true")}>
                <ReportOffIcon fontSize="large" />
              </IconButton> :
                
                  <IconButton aria-label="block"
                    title="Blokiraj korisnika"
                    onClick={handleBlock("false")}>
                    <ReportIcon fontSize="large" />
                  </IconButton> :
                    null
            }

          </Container>
          <Dialog open={open} onClose={handleClose}>
            <DialogTitle id="form-dialog-title">
              Promijeni sliku profila
            </DialogTitle>
            <DialogContent>
              <DialogContentText>
                Upišite link na sliku koju želite postaviti kao sliku profila.
              </DialogContentText>
              <TextField
                autoFocus
                margin="dense"
                id="link"
                label="Link"
                fullWidth
                onChange={(event) =>
                  localStorage.setItem("photo", event.target.value)
                }
              />
            </DialogContent>
            <DialogActions>
              <Button onClick={handleClose} color="primary">
                Odustani
              </Button>
              <Button onClick={handleClose} color="primary">
                Spremi
              </Button>
            </DialogActions>
          </Dialog>
          <Container
            className={classes.requestsContainer}
            maxWidth="lg"
            disableGutters={true}
          >
            <Paper className={classes.tabs}>
              <Tabs
                value={value}
                onChange={handleChange}
                indicatorColor="primary"
                textColor="primary"
                variant="fullWidth"
                aria-label="full width tabs example"
                classes={{ indicator: classes.scrollIndicator }}
              >
                <Tab
                  label="Aktivni zahtjevi"
                  {...a11yProps(0)}
                  classes={{ wrapper: classes.tabWrapper }}
                />
                <Tab
                  label="Izvršeni zahtjevi"
                  {...a11yProps(1)}
                  classes={{ wrapper: classes.tabWrapper }}
                />
                <Tab
                  label="Zahtjevi u obradi"
                  {...a11yProps(2)}
                  classes={{ wrapper: classes.tabWrapper }}
                />
                <Tab
                  label="Blokirani zahtjevi"
                  {...a11yProps(3)}
                  classes={{ wrapper: classes.tabWrapper }}
                />
              </Tabs>
            </Paper>
          </Container>
          {requests ?
            <Container
              className={classes.requestsContainer}
              maxWidth="lg"
              disableGutters={true}
            >
              <SwipeableViews
                axis={theme.direction === "rtl" ? "x-reverse" : "x"}
                index={value}
                onChangeIndex={handleChangeIndex}
                style={{ width: "100%" }}
              >
                <TabPanel value={value} index={0} dir={theme.direction}>
                  <List className={classes.list}>
                    {requests.ACTIVE._embedded.requests.map(mapRequests)}
                  </List>
                </TabPanel>
                <TabPanel value={value} index={1} dir={theme.direction}>
                  <List>
                    {/* requests.FINALIZED._embedded.requests.map(mapRequests) */}
                  </List>
                </TabPanel>
                <TabPanel value={value} index={2} dir={theme.direction}>
                  <List className={classes.list}>

                  </List>
                </TabPanel>
                <TabPanel value={value} index={3} dir={theme.direction}>
                  <List className={classes.list}>
                    {/* requests.BLOCKED._embedded.requests.map(mapRequests) */}
                  </List>
                </TabPanel>
              </SwipeableViews>
            </Container>
            : null
          }
        </div>
      </div>
    );
  }
};

export default Profile;
