import React, { Fragment, Component } from 'react';
import { BrowserRouter as Router, Route, Link, Switch, Redirect } from "react-router-dom";
import Login from './Login';
import Register from './Register';
import Dashboard from './Dashboard';
import Error from './Error';
import style from '../style/App.module.css';

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isAuthed: false,
            userId: -1
        }
    }

    componentDidMount() {
        let token = localStorage.getItem("accessToken");
        let userId = localStorage.getItem("userId")
        if(token !== null && userId !== null) {
            this.setState({
                isAuthed: true,
                userId: userId
            });
        }
        else {
            this.setState({
                isAuthed: false,
                userId: -1
            })
        }
    }

    authorize = (loginData) => {
        localStorage.setItem("accessToken", loginData.accessToken);
        localStorage.setItem("userId", loginData.id);
        this.setState({
            isAuthed: true,
            userId: loginData.id
        });
    }

    handleLogout = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("userId");
        this.setState({
            isAuthed: false,
            userId: -1
        })
    }

    render() {
        console.log(this.state.isAuthed)
        return (
            <Fragment>
                <Router>
                    <Switch>
                        <Route path="/" exact render={() => (
                            this.state.isAuthed ? (
                                <Redirect to="/dashboard" />
                            ) : (
                                <Login authorize={this.authorize} />
                            )
                        )}/>
                        <Route path="/login" exact render={() => (
                            this.state.isAuthed ? (
                                <Redirect to="/dashboard" />
                            ) : (
                                <Login authorize={this.authorize} />
                            )
                        )}/>
                        <Route path="/register" exact render={() => (
                            this.state.isAuthed ? (
                                <Redirect to="/dashboard" />
                            ) : (
                                <Register />
                            )
                        )}/>
                        <Route path="/dashboard" exact render={() => (
                            this.state.isAuthed ? (
                                <Dashboard userId={this.state.userId} handleLogout={this.handleLogout} />
                            ) : (
                                <Redirect to="/login" />
                            )
                        )}/>
                        <Error/>
                    </Switch>
                </Router>
            </Fragment>
        );
    }
}

export default App;
