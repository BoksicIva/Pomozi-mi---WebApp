import React, { useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import Container from '@material-ui/core/Container';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import style from './style/page.module.css'
import Sidebar from './sidebar';
import Userservice from "../service/login-service";
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import TextField from '@material-ui/core/TextField';
import { Link } from "react-router-dom";


const useStyles = makeStyles((theme) => ({
  table: {
    minWidth: 650,
  },
  formControl: {
    margin: theme.spacing(1),
    minWidth: 200,
  },
  selectEmpty: {
    marginTop: theme.spacing(2),
  },
}));

let rows = [];

export default function BasicTable() {
  const classes = useStyles();
  document.body.style = 'background-image: none;';
  const [Users, setUsers] = useState([]);
  const [filter, setFilter] = React.useState('');
  const [sort, setSort] = React.useState('');
  const [value, setValue] = React.useState('');
  const [UsersTemp, setUsersTemp] = useState([]);

  const handleSubmit = (event) => {
    for (let user of UsersTemp) {
      let fullName = user.firstName + " " + user.lastName;
      if (user.lastName.toLowerCase().includes(value.toLowerCase()) ||
        user.firstName.toLowerCase().includes(value.toLowerCase()) ||
        fullName.toLowerCase().includes(value.toLowerCase())) {
          console.log(user._links.self.href);
        rows.push(user);
      }
    }
    console.log(rows);
    setUsers(rows);
    event.preventDefault();
    rows = [];
  };

  const handleChangeInput = (event) => {
    setValue(event.target.value);
  };
  const handleChangeSort = (event) => {
    setSort(event.target.value);
    console.log(event.target.value);
    console.log(typeof sort);

    if (event.target.value === "1") {
      Userservice.getSortedUsers("lastName")
        .then((response) => {
          setUsers(response.data._embedded.users);

          console.log(Users);

          //rows = response.data._embedded.users;
          //console.log(rows);
          //console.log(rows[0]);
        })
        .catch((error) => {
          alert(error);
        })
    }
    else {
      Userservice.getSortedUsers("firstName")
        .then((response) => {
          setUsers(response.data._embedded.users);
          console.log(Users);
          //rows = response.data._embedded.users;
          //console.log(rows);
          //console.log(rows[0]);
        })
        .catch((error) => {
          alert(error);
        })

    }
  };

  useEffect(() => {
    Userservice.getUsers()
      .then((response) => {
        setUsers(response.data._embedded.users);
        setUsersTemp(response.data._embedded.users);
        console.log(Users);
        //rows = response.data._embedded.users;
        //console.log(rows);
        //console.log(rows[0]);
      })
      .catch((error) => {
        alert(error);
      })
  }, []);



  return (
    <>
      <Sidebar />
      <div className={style.empthy1}></div>
      <Container maxWidth="lg">
        <FormControl className={classes.formControl}>
          <InputLabel id="filter-select">Filtriraj korisnike po</InputLabel>
          <Select
            labelId="filter-select"
            id="filter-select"
            value={filter}

          >
            <MenuItem value={1}>kategoriji</MenuItem>
            <MenuItem value={2}>Radijusu udaljenosti</MenuItem>

          </Select>
        </FormControl>

        <FormControl className={classes.formControl}>
          <InputLabel id="filter-select">Sortiraj korisnike po</InputLabel>
          <Select
            labelId="sort-select"
            id="sort-select"
            value={sort}
            onChange={handleChangeSort}
          >
            <MenuItem value={"1"}>Prezimenu</MenuItem>
            <MenuItem value={"2"}>Imenu</MenuItem>

          </Select>
        </FormControl>
        <form onSubmit={handleSubmit}>
          <TextField id="filled-basic" label="Pretraži korisnika" value={value} onChange={handleChangeInput} variant="filled" />

        </form>
        <TableContainer component={Paper}>
          <Table className={classes.table} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>Ime i prezime</TableCell>
                <TableCell align="right">email:</TableCell>
                <TableCell align="right">ocjena</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {Users.map((user) => (
                <TableRow key={user.idUser}>
                  <TableCell component="th" scope="row">
                    <Link to={"/profile/" + user.idUser}>{user.firstName + " " + user.lastName}</Link>
                  </TableCell>
                  <TableCell align="right">{user.email}</TableCell>
                  <TableCell align="right">5</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Container>
    </>
  );
}