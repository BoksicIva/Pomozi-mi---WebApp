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
import './style/page.css'
import Sidebar from './sidebar';
import Userservice from "../service/login-service";


const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
});


let rows = [];

export default function BasicTable() {
  const classes = useStyles();
  document.body.style = 'background-image: none;';
  const [Users, setUsers] = useState([]);
  useEffect(() => {
    Userservice.getUsers()
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
  }, []);



  return (
    <>
      <Sidebar />
      <div className="empthy1"></div>
      <Container maxWidth="lg">
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
                    {user.firstName + " " + user.lastName}
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