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
import ProfileStyle from "./style/profile.module.css";

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

  useEffect(() => {
    console.log(UserService.getUserContext());
    UserService.getUser(UserService.getUserContext().id).then((response) => {
      console.log(response.data);
    });
  });

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
                {localStorage.getItem("firstName")}{" "}
                {localStorage.getItem("lastName")}
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
              Država: {localStorage.getItem("country")}
              <br />
              Mjesto: {localStorage.getItem("town")}
              <br />
              Adresa: {localStorage.getItem("address")}
              <br />
              <div>
                <a
                  href={"mailto: " + localStorage.getItem("email")}
                  style={{ textDecoration: "none", color: "#3f51b5" }}
                >
                  <Typography variant="h5">
                    {localStorage.getItem("email")}
                  </Typography>
                </a>
              </div>
            </Typography>

            <Typography
              variant="h5"
              color="textSecondary"
              style={{ display: about ? "none" : "block" }}
            >
              Ocjena: 5.0
              <br />
              Izvršeni zahtijevi: 7
              <br />
              Zadani zahtijev: 2
              <br />
              Rang: 45
            </Typography>
          </Container>
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
            </Tabs>
          </Paper>
        </Container>
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
                <ListItem alignItems="flex-start">
                  <ListItemAvatar>
                    <Avatar>R</Avatar>
                  </ListItemAvatar>
                  <ListItemText
                    primary="Brunch this weekend?"
                    secondary={
                      <React.Fragment>
                        <Typography
                          component="span"
                          variant="body2"
                          color="textPrimary"
                        >
                          Ali Connors
                        </Typography>
                        {" — I'll be in your neighborhood doing errands this…"}
                      </React.Fragment>
                    }
                  />
                </ListItem>
                <Divider variant="inset" component="li" />
                <ListItem alignItems="flex-start">
                  <ListItemAvatar>
                    <Avatar>H</Avatar>
                  </ListItemAvatar>
                  <ListItemText
                    primary="Summer BBQ"
                    secondary={
                      <React.Fragment>
                        <Typography
                          component="span"
                          variant="body2"
                          color="textPrimary"
                        >
                          to Scott, Alex, Jennifer
                        </Typography>
                        {" — Wish I could come, but I'm out of town this…"}
                      </React.Fragment>
                    }
                  />
                </ListItem>
                <Divider variant="inset" component="li" />
                <ListItem alignItems="flex-start">
                  <ListItemAvatar>
                    <Avatar>J</Avatar>
                  </ListItemAvatar>
                  <ListItemText
                    primary="Oui Oui"
                    secondary={
                      <React.Fragment>
                        <Typography
                          component="span"
                          variant="body2"
                          color="textPrimary"
                        >
                          Sandra Adams
                        </Typography>
                        {" — Do you have Paris recommendations? Have you ever…"}
                      </React.Fragment>
                    }
                  />
                </ListItem>
              </List>
            </TabPanel>
            <TabPanel value={value} index={1} dir={theme.direction}>
              <List>
                <ListItem alignItems="flex-start">
                  <ListItemAvatar>
                    <Avatar>R</Avatar>
                  </ListItemAvatar>
                  <ListItemText
                    primary="Brunch this weekend?"
                    secondary={
                      <React.Fragment>
                        <Typography
                          component="span"
                          variant="body2"
                          color="textPrimary"
                        >
                          Ali Connors
                        </Typography>
                        {" — I'll be in your neighborhood doing errands this…"}
                      </React.Fragment>
                    }
                  />
                </ListItem>
                <Divider variant="inset" component="li" />
                <ListItem alignItems="flex-start">
                  <ListItemAvatar>
                    <Avatar>H</Avatar>
                  </ListItemAvatar>
                  <ListItemText
                    primary="Summer BBQ"
                    secondary={
                      <React.Fragment>
                        <Typography
                          component="span"
                          variant="body2"
                          color="textPrimary"
                        >
                          to Scott, Alex, Jennifer
                        </Typography>
                        {" — Wish I could come, but I'm out of town this…"}
                      </React.Fragment>
                    }
                  />
                </ListItem>
                <Divider variant="inset" component="li" />
                <ListItem alignItems="flex-start">
                  <ListItemAvatar>
                    <Avatar>J</Avatar>
                  </ListItemAvatar>
                  <ListItemText
                    primary="Oui Oui"
                    secondary={
                      <React.Fragment>
                        <Typography
                          component="span"
                          variant="body2"
                          color="textPrimary"
                        >
                          Sandra Adams
                        </Typography>
                        {" — Do you have Paris recommendations? Have you ever…"}
                      </React.Fragment>
                    }
                  />
                </ListItem>
              </List>
            </TabPanel>
          </SwipeableViews>
        </Container>
      </div>
    </div>
  );
};

export default Profile;
