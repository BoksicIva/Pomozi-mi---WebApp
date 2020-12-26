import React from 'react';
import './App.css';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './components/login';
import Registration from './components/registration';
import Page from './components/requestLoader';
import home from './components/home';
import BasicTable from './components/userList';

function App() {
  return (
    <>
    <BrowserRouter>
    <Switch>
      <Route path='/page' component={Page}/>
      <Route path='/list' component={BasicTable}/>
      <Route path='/home' component={home}/>
      <Route path='/login' component={Login}/>
      <Route path='/register' exact component={Registration}/>
      <Route path='/' component={Login}/>
    </Switch>
    </BrowserRouter>
    </>
  );
}

export default App;
