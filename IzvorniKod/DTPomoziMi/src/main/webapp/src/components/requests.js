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

const useStyles = makeStyles((theme) => ({
    root: {
        maxWidth: 345,
    },
    media: {
        height: 0,
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

export default function RecipeReviewCard() {
    const classes = useStyles();
    const [expanded, setExpanded] = React.useState(false);
    const [requests, setRequests] = React.useState([]);

    useEffect(() => {
        RequestService.getRequests()
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

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    return (
        <>
            <Sidebar />
            <Container>
                {requests.map((request) => (
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

                                <Link to={"/user/" + request.author.idUser}>{request.author.firstName + " " + request.author.lastName}</Link>
                            }
                            subheader={request.author.email}
                        />

                        <CardContent>
                            <Typography variant="body2" color="textSecondary" component="p">
                                {request.description}
                            </Typography>
                        </CardContent>
                        <CardActions disableSpacing>
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
                ))};
            </Container>
        </>
    );
}
