import React, { lazy, Suspense } from 'react';
import './App.css';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

const Login = lazy(() => import("./components/login"));
const Home = lazy(() => import("./components/home"));
const Registration = lazy(() => import("./components/registration"));
const RequestLoader = lazy(() => import("./components/requestLoader"));
const UserList = lazy(() => import("./components/userList"));
const ReqList = lazy(() => import("./components/requests"));

function App() {
  return (

    <>
      <BrowserRouter>
        <Suspense fallback={<div>Loading...</div>}>
          <Switch>
            <Route path='/requests' component={ReqList} />
            <Route path='/page' component={RequestLoader} />
            <Route path='/list' component={UserList} />
            <Route path='/home' component={Home} />
            <Route path='/login' component={Login} />
            <Route path='/register' exact component={Registration} />
            <Route path='/' component={Login} />
          </Switch>
        </Suspense>
      </BrowserRouter>
    </>
  );
}

export default App;
