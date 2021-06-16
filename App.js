import React, { PureComponent } from 'react';
import { StatusBar } from 'expo-status-bar';
import { View } from "react-native";
import { 
  Provider as PaperProvider,
  DarkTheme,
  Appbar,
  Menu, 
  Divider,
  Button,
  DefaultTheme
} from 'react-native-paper';
import { Route, Switch, withRouter, withHistory } from 'react-router-native';
import SelectVoice from "./pages/SelectVoice.js";
import RecordAudio from "./pages/RecordAudio.js";
import UnlockPremium from "./pages/UnlockPremium.js";
import ConvertAudio from "./pages/ConvertAudio.js";

const darkTheme = {
  ...DarkTheme,
  colors: {
    ...DarkTheme.colors,
    surface: "rgb(40, 40, 40)"
  },
  adaptive: true,
}

const lightTheme = DefaultTheme;

const voices = [
  {
    name: "Donald Trump",
    image: require('./assets/imgs/trump_512x512.png'),
  },
  { 
    name: "Kim Kardashian",
    image: require('./assets/imgs/kim_kardashian_512x512.png'),
  },
  {
    name: "Kanye West",
    image: require('./assets/imgs/kanye_west_512x512.png'),
  },
  {
    name: "Justin Bieber",
    image: require('./assets/imgs/justin_bieber_512x512.png'),
  },
  {
    name: "Billie Eilish",
    image: require('./assets/imgs/billie_eilish_512x512.png'),
  }
];

class App extends PureComponent {
  state = {
    progress: 0.1232,
    isMenuOpen: false,
    isDarkMode: true
  }

  onUseVoice = name => {
    const {history} = this.props;
    history.push("/record-audio"); 
  }

  getTitle = () => {
    const {history} = this.props;
    switch(history.location.pathname) { 
      case "/record-audio":
        return "Record Audio";
      case "/unlock-premium":
        return "Unlock Premium";
      case "/convert-audio":
          return "Converting Audio";
      default:
        return "Select a Voice";
    }
  }

  goToPremium = () => {
    const {history } = this.props;
    history.push("/unlock-premium");
  }

  onCancelLoading = () => {
    const {history } = this.props;
    history.goBack();
  }

  openMenu = () => {
    this.setState({isMenuOpen: true});
  }

  closeMenu = () => {
    this.setState({isMenuOpen: false});
  }

  toggleDarkMode = () => {
    const {isDarkMode} = this.state;
    this.setState({isDarkMode: !isDarkMode, isMenuOpen: false});
  }

  render() {
    const { history } = this.props;
    const { progress, isMenuOpen, isDarkMode } = this.state;
    return (
      <PaperProvider theme={isDarkMode ? darkTheme : lightTheme}>
        <Appbar.Header style={{elevation: 4}}>
          {history.index > 0 && <Appbar.BackAction onPress={history.goBack}/>}
          <Appbar.Content title={this.getTitle()}/>
          <Menu 
            visible={isMenuOpen} 
            onDismiss={this.closeMenu} 
            anchor={<Appbar.Action icon="dots-vertical" color="white" 
              onPress={this.openMenu} />
            }
          >
            <Menu.Item title="Buy Premium" onPress={
                () => {
                  this.goToPremium();
                  this.closeMenu();
                }
              } 
            />
            <Menu.Item title={isDarkMode ? "Light Mode": "Dark Mode"} onPress={this.toggleDarkMode} />
          </Menu>
        </Appbar.Header>
        <View style={{backgroundColor: isDarkMode ? "#121212": "#f5f5f5", flex: 1}}>
          <Switch>
            <Route path="/record-audio" render={() => <RecordAudio isDarkMode={isDarkMode}/>}/>
            <Route path="/unlock-premium" render={() => <UnlockPremium/>}/>
            <Route path="/convert-audio" render={() => <ConvertAudio onCancelLoading={this.onCancelLoading} progress={progress}/>}/>
            <Route path="/" render={() => <SelectVoice voices={voices} onUseVoice={this.onUseVoice}/>}/>
          </Switch>
        </View>
        <StatusBar style="auto" /> 
      </PaperProvider>
    );
  }
}

export default withRouter(App);