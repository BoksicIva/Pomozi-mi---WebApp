import React, { useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Card from '@material-ui/core/Card';
import Link from '@material-ui/core/Link'
import CardHeader from '@material-ui/core/CardHeader';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import Collapse from '@material-ui/core/Collapse';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { red } from '@material-ui/core/colors';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import Sidebar from './sidebar';
import RequestService from '../service/login-service';
import Container from '@material-ui/core/Container';
import style from "./style/page.module.css";
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button';
import {
    GoogleMap,
    useLoadScript,
    Marker,
} from "@react-google-maps/api";
import { propTypes } from 'react-bootstrap/esm/Image';

const useStyles = makeStyles((theme) => ({
    root: {
        width:  '65vw',
    },
    media: {
        height: 100,
        paddingTop: '56.25%', // 16:9
    },
    expand: {
        transform: 'rotate(0deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
        }),
    },
    expandOpen: {
        transform: 'rotate(180deg)',
    },
    avatar: {
        backgroundColor: red[500],
    },
}));

const libraries = ["places"];
const mapContainerStyle = {
    height: "100vh",
    width: "60vw",
};
const center = {
    lat: 45.815399,
    lng: 15.966568,
};
const options = {
    disableDefaultUI: true,
    zoomControl: true,
};


export default function RecipeReviewCard(props) {

    useEffect(() => {
        RequestService.getRequests(1)
            .then((response) => {
                setRequests(response.data._embedded.requests);
                //setUsersTemp(response.data._embedded.users);
                console.log(response.data._embedded.requests);
                //rows = response.data._embedded.users;
                //console.log(rows);
                //console.log(rows[0]);
            })
            .catch((error) => {
                alert(error);
            })
    }, []);

    const { isLoaded, loadError } = useLoadScript({
        googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
        libraries,
    });

    const classes = useStyles();
    const [expanded, setExpanded] = React.useState(false);
    const [requests, setRequests] = React.useState([]);
    const [value, setValue] = React.useState('');
    const [notSent, setNotSent] = React.useState(true);
    const [lat, setLat] = React.useState('');
    const [lng, setLng] = React.useState(true);


    if (loadError) return "Error";
    if (!isLoaded) return "Loading...";


    const handleSubmit = (event) => {
        RequestService.getRequests(value)
            .then((response) => {
                setRequests(response.data._embedded.requests);
                //setUsersTemp(response.data._embedded.users);
                console.log(response.data._embedded.requests);
                //rows = response.data._embedded.users;
                //console.log(rows);
                //console.log(rows[0]);
            })
            .catch((error) => {
                alert(error);
            })
        event.preventDefault();
    };


    const handleChangeInput = (event) => {
        setValue(event.target.value);
    };

    const refreshPage = () => {
        window.location.reload(false);
    }



    
    
    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    const handleRequestClick = value => () => {
        console.log(value);

        RequestService.sendExecution(value.idRequest)
            .then((response) => {
                setLat(value.location.latitude);
                setLng(value.location.longitude);
                setNotSent(false);
                console.log(response);

            })
            .catch((error) => {
                alert(error);
            })
    }

    return (
            <>
            <Sidebar />
            <div></div>
            <div className={style.background}>
            <form onSubmit={handleSubmit}>
                <TextField id="filled-basic" label="Radius zahtjeva" value={value} onChange={handleChangeInput} variant="filled" />

            </form>
            <Container>
                {notSent ?
                    requests.map((request) => (
                        <Card className={classes.root}>
                            <CardHeader
                                avatar={
                                    <Avatar aria-label="recipe" className={classes.avatar}>
                                        R
                             </Avatar>
                                }
                                action={
                                    <IconButton aria-label="settings">
                                        <MoreVertIcon />
                                    </IconButton>
                                }
                                title={

                                    <Link onClick={(event) => {props.history.push("/profile/" + request.author.idUser)}}>{request.author.firstName + " " + request.author.lastName}</Link>
                                }
                                subheader={request.author.email}
                            />

                            <CardContent>
                                <Typography variant="body2" color="textSecondary" component="p">
                                    {request.description}
                                </Typography>
                            </CardContent>
                            <CardActions disableSpacing>

                                <Button size="small" onClick={handleRequestClick(request)}>Izvrši zahtjev</Button>

                                <IconButton
                                    className={clsx(classes.expand, {
                                        [classes.expandOpen]: expanded,
                                    })}
                                    onClick={handleExpandClick}
                                    aria-expanded={expanded}
                                    aria-label="show more"
                                >
                                    <ExpandMoreIcon />
                                </IconButton>
                            </CardActions>
                            <Collapse in={expanded} timeout="auto" unmountOnExit>
                                <CardContent>
                                    <Typography paragraph>Rok izvrsavanja:</Typography>

                                    <Typography paragraph>
                                        {request.tstmp}
                                    </Typography>
                                    <Typography paragraph>
                                        Lokacija:
                                </Typography>
                                    <Typography paragraph>
                                        Grad: {" " + request.location.town}
                                        <br></br>
                                    Adresa: {" " + request.location.adress}
                                    </Typography>
                                </CardContent>
                            </Collapse>
                        </Card>
                    )) :
                    <>
                        <div>
                            <button onClick={refreshPage}>Povratak na zahtjeve</button>
                        </div>
                        <h1>
                            Zahtjev uspjesno poslan
                    </h1>
                        <h2>
                            Lokacija zahtjeva:
                    </h2>

                        <div>
                            <GoogleMap mapContainerStyle={mapContainerStyle}
                                zoom={15}
                                center={center}
                                options={options}
                                onClick={(event) => {
                                    
                                    setLat(event.latLng.lat())
                                    setLng(event.latLng.lng())
                                }}
                            >

                                <Marker
                                    key={16}
                                    position={{ lat: lat, lng: lng }}
                                />
                            </GoogleMap>
                        </div>
                    </>
                }
            </Container>
            </div>
        </>
    );
}
