import React, { Fragment, Component } from 'react';
import logo from '../logo.svg';
import '../style/App.css';

import img1 from '../img/1.jpg'

import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'

class App extends Component {
  render() {
    return (
        <Fragment>
        <Row>
            <Col md="auto">
            <Card style={{ width: '18rem' }}>
                <Card.Img variant="top" src={img1} />
                <Card.Body>
                    <Card.Title>Card Title</Card.Title>
                    <Card.Text>
                    Some quick example text to build on the card title and make up the bulk of
                    the card's content.
                    </Card.Text>
                    <Button variant="primary">Go somewhere</Button>
                </Card.Body>
            </Card>
            </Col>
            
        </Row>
        </Fragment>
        
    );
  }
}

export default App;
