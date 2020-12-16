import React from 'react';
import './App.css';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './components/login';
import Registration from './components/registration';
<<<<<<< HEAD
import Page from './components/page';
import home from './components/home';
=======
import Home from './components/home';
>>>>>>> bdfc6bbc144e41dccf30bec1db709c753d7ea87f

function App() {
  return (
    <>
    <BrowserRouter>
    <Switch>
      <Route path='/page' component={Page}/>
      <Route path='/home' component={home}/>
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
