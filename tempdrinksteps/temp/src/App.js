import React from 'react';
import ClickNHold from 'react-click-n-hold';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Button, Container, Row, Card } from 'react-bootstrap';


class DrinkStep extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      value: 'yeet',
    }
  }

  end(e, enough) {
    console.log('End');
    if(enough){
      console.log('Click released after enough time');
    }else{
      console.log('Click released too soon');
    }
  }

  clickNHold(e) {
    console.log('Click and Hold');
  }

  render() {
    return (
      <Container>
        <Row>
          <ClickNHold
            time={2}
            onStart={(e) => this.setState({value: 'Start'})}
            onClickNHold={() => this.clickNHold}
            onEnd={(e, enough) => 
              this.setState(enough ? {value: 'Click released after enough time'} : {value: 'Click released too soon'})
            } >
            <Card className="bg-card">
              <Card.Body>
                <Button>Click and hold</Button>
              </Card.Body>
            </Card>
          </ClickNHold>
        </Row>
        <Row>
          <p>{this.state.value}</p>
        </Row>
      </Container>
    );
  }
}

class App extends React.Component {


  render() {
    return (
      <DrinkStep />
    );
  }
}

export default App;
