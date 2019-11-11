import React, { Fragment, Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch, Redirect } from "react-router-dom";
import Login from './Login';
import Register from './Register';
import ProfessionalDashboard from './ProfessionalDashboard';
import style from '../style/App.module.css';


class App extends Component {
  render() {
    return (
        <Fragment>
            <Router>
                <Switch>
                    <Route path="/login" exact>
                        <Login />
                    </Route>
                    <Route path="/register" exact>
                        <Register />
                    </Route>
                    <Route path="/prodash" exact>
                        <ProfessionalDashboard />
                    </Route>
                    <Login />
                </Switch>
            </Router>
        </Fragment>
    );
  }
}

export default App;
