import React from 'react';
import './App.css';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './components/login';
import Registration from './components/registration';
import Home from './components/home';

function App() {
  return (
    <>
    <BrowserRouter>
    <Switch>
      <Route path='/login' component={Login}/>
      <Route path='/register' exact component={Registration}/>
      <Route path='/home' exact component={Home}/>
      <Route path='/' component={Login}/>
    </Switch>
    </BrowserRouter>
    </>
  );
}

export default App;
