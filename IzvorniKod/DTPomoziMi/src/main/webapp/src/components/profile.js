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
import CreateIcon from "@material-ui/icons/Create";
import ReportIcon from "@material-ui/icons/Report";
import ReportOffIcon from "@material-ui/icons/ReportOff";
import StarBorderIcon from "@material-ui/icons/StarBorder";
import Star from "@material-ui/icons/Star";
import Rating from "@material-ui/lab/Rating";
import RatingService from "../service/rating-service";
//https://p16-sg.tiktokcdn.com/img/musically-maliva-obj/1651168661856262~c5_720x720.jpeg

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
    marginRight: "2vw",
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

  ratingContainer: {
    marginBottom: "10px",
  },
}));

const Profile = (props) => {
  const classes = useStyles();
  const theme = useTheme();
  document.body.style = "background-image: none; background-color: white";

  const [photoDialog, setPhotoDialog] = useState(false);
  const [reqDialog, setReqDialog] = useState(false);
  const [deleteDialog, setDeleteDialog] = useState(false);
  const [gradeDialog, setGradeDialog] = useState(false);
  const [about, setAbout] = useState(true);
  const [value, setValue] = useState(0);
  const [userData, setUserData] = useState(null);
  const [userStatistics, setUserStatistics] = useState(null);
  const [requests, setRequests] = useState(null);
  const [dialogReq, setDialogReq] = useState(null);
  const [updateReqs, setUpdateReqs] = useState({});
  const [isUser, setUser] = useState(false);
  const [isAdmin, setAdmin] = useState(false);
  const [isBlocked, setBlocked] = useState(false);

  const [rating, setRating] = useState({
    ratingComment: null,
    ratingGrade: null,
  });

  function handleChange(event, newValue) {
    setValue(newValue);
  }

  function handleChangeIndex(index) {
    setValue(index);
  }

  const openPhotoDialog = () => {
    setPhotoDialog(true);
  };

  const closePhotoDialog = () => {
    setPhotoDialog(false);
  };

  const openReqDialog = () => {
    setReqDialog(true);
  };

  const closeReqDialog = () => {
    setReqDialog(false);
  };

  const openDeleteDialog = () => {
    setDeleteDialog(true);
  };

  const closeDeleteDialog = () => {
    setDeleteDialog(false);
  };

  const openGradeDialog = () => {
    setGradeDialog(true);
  };

  const closeGradeDialog = () => {
    setGradeDialog(false);
  };

  const handleAbout = () => {
    setAbout(!about);
    console.log(userStatistics);
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
    })
      .catch((error) => {
        alert(error);
      });

    UserService.getUserStatistics(props.match.params.id)
      .then((response) => {
        setUserStatistics(response.data);
      })
      .catch((error) => {
        alert(error);
      });

    if (props.match.params.id == user.id) {
      UserService.getAuthored(user.id)
        .then((response) => {
          setRequests(response.data);
        })
        .catch((error) => {
          alert(error);
        });
    } else {
      setRequests();
    }
    console.log("Refreshao sam se");
  }, [updateReqs, props.match.params.id]);

  const mapRequests = (request) => {
    return (
      <>
        <ListItem
          button={
            request.status === "ACTIVE" || request.status === "BLOCKED"
              ? true
              : false
          }
          onClick={() => {
            if (request.status === "ACTIVE" || request.status === "BLOCKED") {
              setDialogReq(request);
              openReqDialog();
            }
          }}
        >
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

  return (
    <div>
      <Sidebar />

      {/*dialog for editing a request*/}
      <Dialog open={reqDialog} onClose={closeReqDialog} maxWidth="sm" fullWidth>
        <DialogTitle>
          Zahtjev od:{" "}
          {dialogReq
            ? dialogReq.author.firstName + " " + dialogReq.author.lastName
            : null}
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            STATUS: {dialogReq ? dialogReq.status : null}
          </DialogContentText>
          <TextField
            label="Opis zahtjeva"
            defaultValue={dialogReq ? dialogReq.description : null}
            fullWidth
            multiline
            onChange={(description) =>
              (dialogReq.description = description.target.value)
            }
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={openDeleteDialog} color="secondary">
            Izbriši
          </Button>
          <Button
            onClick={() => {
              closeReqDialog();
              if (dialogReq) {
                if (dialogReq.status === "ACTIVE") {
                  UserService.blockRequest(dialogReq.idRequest).then(
                    (response) => {
                      setRequests(response.data);
                      setUpdateReqs({});
                    }
                  );
                } else if (dialogReq.status === "BLOCKED") {
                  UserService.unblockRequest(dialogReq.idRequest).then(
                    (response) => {
                      setRequests(response.data);
                      setUpdateReqs({});
                    }
                  );
                }
              }
            }}
            color="secondary"
          >
            {dialogReq
              ? dialogReq.status === "ACTIVE"
                ? "Blokiraj"
                : null
              : null}
            {dialogReq
              ? dialogReq.status === "BLOCKED"
                ? "Aktiviraj"
                : null
              : null}
          </Button>
          <Button
            onClick={() => {
              closeReqDialog();
              UserService.updateRequest(dialogReq.idRequest, dialogReq).then(
                (response) => {
                  setRequests(response.data);
                  setUpdateReqs({});
                }
              );
            }}
            color="primary"
          >
            Spremi
          </Button>
        </DialogActions>
      </Dialog>

      {/* dialog for changing profile photo */}
      <Dialog
        open={photoDialog}
        onClose={closePhotoDialog}
        maxWidth="sm"
        fullWidth
      >
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
          <Button onClick={closePhotoDialog} color="primary">
            Odustani
          </Button>
          <Button onClick={closePhotoDialog} color="primary">
            Spremi
          </Button>
        </DialogActions>
      </Dialog>

      {/*dialog for confirming deleting a request*/}
      <Dialog open={deleteDialog} onClose={closeDeleteDialog}>
        <DialogTitle>
          {"Jeste li sigurni da želite izbrisati ovaj zahtjev?"}
        </DialogTitle>
        <DialogActions>
          <Button
            onClick={() => {
              closeDeleteDialog();
              closeReqDialog();
              UserService.deleteRequest(dialogReq.idRequest).then(
                (response) => {
                  setRequests(response.data);
                  setUpdateReqs({});
                }
              );
            }}
            color="secondary"
          >
            Izbriši
          </Button>
          <Button onClick={closeDeleteDialog} color="primary">
            Odustani
          </Button>
        </DialogActions>
      </Dialog>

      {/*dialog for grading a user*/}
      <Dialog
        fullWidth
        maxWidth="sm"
        open={gradeDialog}
        onClose={closeGradeDialog}
      >
        <DialogTitle>
          Ocijenite korisnika:
          {userData ? " " + userData.firstName + " " + userData.lastName : null}
        </DialogTitle>
        <DialogContent style={{ justifyContent: "center" }}>
          <Rating
            name="customized-empty"
            value={rating.ratingGrade ? rating.ratingGrade : 0}
            precision={1}
            emptyIcon={<StarBorderIcon fontSize="large" />}
            icon={<Star fontSize="large" />}
            size="large"
            classes={{ root: classes.ratingContainer }}
            onChange={(event, grade) => {
              setRating({
                ratingComment: rating.ratingComment,
                ratingGrade: grade,
              });
            }}
          />
          <TextField
            value={rating.ratingComment ? rating.ratingComment : undefined}
            label="Komentar"
            fullWidth
            multiline
            onChange={(comment) => {
              setRating({
                ratingComment: comment.target.value,
                ratingGrade: rating.ratingGrade,
              });
            }}
          />
        </DialogContent>
        <DialogActions>
          <Button
            disabled={
              rating.ratingComment === null || rating.ratingGrade === null
                ? true
                : false
            }
            onClick={() => {
              closeGradeDialog();
              RatingService.rateUser(userData.idUser, {
                comment: rating.ratingComment,
                rate: rating.ratingGrade,
              });
              setUpdateReqs({});
            }}
            color="secondary"
          >
            Ocijeni
          </Button>
          <Button onClick={closeGradeDialog} color="primary">
            Odustani
          </Button>
        </DialogActions>
      </Dialog>

      <div className={classes.body}>
        <Container className={classes.profileInfo} maxWidth="lg">
          <IconButton
            className={classes.avatarButton}
            onClick={openPhotoDialog}
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
            <Container maxWidth={false} disableGutters={true}>
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
              {isUser ? null : isAdmin ? (
                isBlocked ? (
                  <IconButton
                    classes={{
                      label: classes.visibilityLabel,
                      root: classes.visibilityRoot,
                    }}
                    className={classes.visibilityButton}
                    onClick={handleBlock("true")}
                  >
                    <ReportOffIcon fontSize="large" />
                  </IconButton>
                ) : (
                    <IconButton
                      classes={{
                        label: classes.visibilityLabel,
                        root: classes.visibilityRoot,
                      }}
                      className={classes.visibilityButton}
                      onClick={handleBlock("false")}
                    >
                      <ReportIcon fontSize="large" />
                    </IconButton>
                  )
              ) : null}
              <IconButton
                disabled={isUser ? true : false}
                classes={{
                  label: classes.visibilityLabel,
                  root: classes.visibilityRoot,
                }}
                onClick={openGradeDialog}
                className={classes.visibilityButton}
              >
                <CreateIcon />
              </IconButton>
            </Container>

            <Container maxWidth={false} disableGutters={true}>
              <Typography variant="h4" color="textSecondary">
                {userData ? userData.firstName + " " + userData.lastName : null}
              </Typography>
              <Typography
                variant="h5"
                color="textSecondary"
                style={{ display: about ? "flex" : "none" }}
              >
                {isUser ? (
                  <p>
                    Država: {userData ? userData.location.state : null}
                    <br />
                    Mjesto: {userData ? userData.location.town : null}
                    <br />
                    Adresa: {userData ? userData.location.adress : null}
                    <br />
                  </p>
                ) : null}
              </Typography>

              <Typography
                variant="h5"
                color="textSecondary"
                style={{ display: about ? "none" : "flex" }}
              >
                Ocjena: {userStatistics ? userStatistics.avgGrade : null}
                <br />
                Izvršeni zahtjevi:{" "}
                {userStatistics ? userStatistics.numExecutedR : null}
                <br />
                Zadani zahtjevi:{" "}
                {userStatistics ? userStatistics.numAuthoredR : null}
                <br />
                Rang: {userStatistics ? userStatistics.rank : null}
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
        </Container>

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
              {requests
                ? requests.ACTIVE
                  ? Object.keys(requests.ACTIVE).length === 0 &&
                    requests.ACTIVE.constructor === Object
                    ? null
                    : requests.ACTIVE._embedded.requests.map(mapRequests)
                  : null
                : null}
            </List>
          </TabPanel>
          <TabPanel value={value} index={1} dir={theme.direction}>
            <List>
              {requests
                ? requests.FINALIZED
                  ? Object.keys(requests.FINALIZED).length === 0 &&
                    requests.FINALIZED.constructor === Object
                    ? null
                    : requests.FINALIZED._embedded.requests.map(mapRequests)
                  : null
                : null}
            </List>
          </TabPanel>
          <TabPanel value={value} index={2} dir={theme.direction}>
            <List className={classes.list}>
              {requests
                ? requests.EXECUTING
                  ? Object.keys(requests.EXECUTING).length === 0 &&
                    requests.EXECUTING.constructor === Object
                    ? null
                    : requests.EXECUTING._embedded.requests.map(mapRequests)
                  : null
                : null}
            </List>
          </TabPanel>
          <TabPanel value={value} index={3} dir={theme.direction}>
            <List className={classes.list}>
              {requests
                ? requests.BLOCKED
                  ? Object.keys(requests.BLOCKED).length === 0 &&
                    requests.BLOCKED.constructor === Object
                    ? null
                    : requests.BLOCKED._embedded.requests.map(mapRequests)
                  : null
                : null}
            </List>
          </TabPanel>
        </SwipeableViews>
      </Container>
    </div>
    </div >
  );
};

export default Profile;
