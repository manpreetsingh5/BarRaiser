import React, { Fragment, Component } from 'react';
import style from '../style/Home.module.css';

import Row from 'react-bootstrap/Row'

class Home extends Component {
    render() {
        return (
            <Fragment>
                <Row className={style.container}>
                    <div className={style.titleDiv}>
                        <h3>Home</h3>
                    </div>
                </Row>
            </Fragment>
        );
    }
}

export default Home;
