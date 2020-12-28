import React, { lazy, Suspense } from "react";
import "./App.css";
import { BrowserRouter, Switch, Route } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

const Login = lazy(() => import("./components/login"));
const Home = lazy(() => import("./components/home"));
const Registration = lazy(() => import("./components/registration"));
const RequestLoader = lazy(() => import("./components/requestLoader"));
const UserList = lazy(() => import("./components/userList"));
const Profile = lazy(() => import("./components/profile"));
//ova komponenta je dodana da se maknu ruzni crni okviri na svakom <a> i <button> sve dok se prvi put ne stisne TAB
const AccessibleFocusOutline = lazy(() =>
  import("./components/accessibleFocusOutline")
);

function App() {
  return (
    <>
      <BrowserRouter>
        <Suspense fallback={<div>Loading...</div>}>
          <AccessibleFocusOutline>
            <Switch>
              <Route path="/page" component={RequestLoader} />
              <Route path="/profile" component={Profile} />
              <Route path="/list" component={UserList} />
              <Route path="/home" component={Home} />
              <Route path="/login" component={Login} />
              <Route path="/register" exact component={Registration} />
              <Route path="/" component={Login} />
            </Switch>
          </AccessibleFocusOutline>
        </Suspense>
      </BrowserRouter>
    </>
  );
}

export default App;
