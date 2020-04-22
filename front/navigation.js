// Navigation/Navigation.js

import React from 'react' // N'oubliez pas l'import de React ici. On en a besoin pour rendre nos components React Native Image ! 
import { StyleSheet, Image, Easing, Animated } from 'react-native';
import { Ionicons, FontAwesome, MaterialCommunityIcons, AntDesign } from '@expo/vector-icons';
import { createStackNavigator, createAppContainer, createBottomTabNavigator } from 'react-navigation'
import LoginScreen from "./components/Screens/LoginScreen";
import CoronaScreen from "./components/Screens/CoronaScreen";
import DetailsScreen from "./components/Screens/DetailsScreen";
import ProfileScreen from "./components/Screens/ProfileScreen";
import ChallengesScreen from "./components/Screens/ChallengesScreen";
import ChallengeDetailScreen from "./components/Screens/ChallengeDetailScreen";
import CategoryScreen from "./components/Screens/CategoryScreen";
import ValidationChallengeScreen from "./components/Screens/ValidationChallengeScreen";
import ChallengesByCategoryScreen from "./components/Screens/ChallengesByCategoryScreen";

const Covid19Tab = createStackNavigator(
  {
    Covid19: {
      screen: CoronaScreen,
      navigationOptions: {
        title: 'COVID-19',
        headerStyle: {
          backgroundColor: '#ffffff',
        },
        headerTintColor: '#980740',
        headerTitleStyle: {
          fontWeight: '200',
          fontSize: 30,
        },        
      }
    },
    Details: {
      screen : DetailsScreen,
      navigationOptions: {
        title: 'QUI SOMMES-NOUS',
            headerStyle: {
              backgroundColor: '#3d9d84',
            },
            headerTintColor: '#ffffff',
            headerTitleStyle: {
              fontWeight: '200',
              fontSize: 30,
            },
      }
  }
  },
  )

const CategoryTab = createStackNavigator(
    {
      Category: {
        screen: CategoryScreen,
        navigationOptions: {
          title: 'CATÉGORIES',
          headerStyle: {
            backgroundColor: '#3d9d84',
          },
          headerTintColor: '#ffffff',
          headerTitleStyle: {
            fontWeight: '200',
            fontSize: 30,
          },
          headerBackTitle:"Catégories",
        }
      },
        ChallengesByCategory: {
          screen: ChallengesByCategoryScreen,
          navigationOptions: {
            title: '',
            headerStyle: {
              backgroundColor: '#3d9d84',
            },
            headerTintColor: '#ffffff',
            headerTitleStyle: {
              fontWeight: '200',
              fontSize: 30,
            },
            headerBackTitle:"Défis",
          }
        },
        ChallengeDetail: {
          screen: ChallengeDetailScreen,
          navigationOptions: {
            title: '',
            headerStyle: {
              backgroundColor: '#3d9d84',
            },
            headerTintColor: '#ffffff',
            headerTitleStyle: {
              fontWeight: '200',
              fontSize: 30,
            },
            headerBackTitle:"Défis",
          }
        },
        Validation: {
          screen: ValidationChallengeScreen,
          navigationOptions: {
            title: 'VALIDATION',
            headerStyle: {
              backgroundColor: '#3d9d84',
            },
            headerTintColor: '#ffffff',
            headerTitleStyle: {
              fontWeight: '200',
              fontSize: 30,
            },
          }
        },
    },
)

const ChallengesTab = createStackNavigator(
    {
      Challenges: {
        screen: ChallengesScreen,
        navigationOptions: {
          title: 'DÉFIS',
          headerStyle: {
            backgroundColor: '#3d9d84',
          },
          headerTintColor: '#ffffff',
          headerTitleStyle: {
            fontWeight: '200',
            fontSize: 30,
          },
          headerBackTitle:"Défis",
        }
      },
        ChallengeDetail: {
          screen: ChallengeDetailScreen,
          navigationOptions: {
            title: '',
            headerStyle: {
              backgroundColor: '#3d9d84',
            },
            headerTintColor: '#ffffff',
            headerTitleStyle: {
              fontWeight: '200',
              fontSize: 30,
            },
            headerBackTitle:"Défis",
          }
        },
        Validation: {
          screen: ValidationChallengeScreen,
          navigationOptions: {
            title: 'VALIDATION',
            headerStyle: {
              backgroundColor: '#3d9d84',
            },
            headerTintColor: '#ffffff',
            headerTitleStyle: {
              fontWeight: '200',
              fontSize: 30,
            },
            headerBackTitle:"Défis",
          }
        },
    },
)

const ProfilTab = createStackNavigator(
  {
    Profil: {
      screen: ProfileScreen,
      navigationOptions: {
        title: 'PROFIL',
        headerStyle: {
          backgroundColor: '#3d9d84',
        },
        headerTintColor: 'white',
        headerTitleStyle: {
          fontWeight: '200',
          fontSize: 30,
        },
        animationTypeForReplace: "pop",
      }
    },
  },
)


// const DetailsTab = createStackNavigator(
//   {
//     Details: {
//       screen: DetailsScreen,
//       navigationOptions: {
//         title: 'DÉTAILS',
//         headerStyle: {
//           backgroundColor: '#ffffff',
//         },
//         headerTintColor: 'black',
//         headerTitleStyle: {
//           fontWeight: '200',
//           fontSize: 30,
//         },
//         animationTypeForReplace: "pop",
//       }
//     },
//   },
// )

const Tabs = createBottomTabNavigator(
    {
        "Covid-19" : {
            screen : Covid19Tab,
            navigationOptions: {
                tabBarIcon: ({ focused}) => {
                  let iconName = 'warning';
                  let color = focused ? '#980740' : 'gray';
                  return <AntDesign name={iconName} size={28} color={color} />;
                },
                title: 'Covid-19',
              headerStyle: {
                backgroundColor: '#f4511e',
              },
              headerTintColor: '#fff',
              headerTitleStyle: {
                fontWeight: 'bold',
              },
            },
            
        },
        "Catégories": {
            screen : CategoryTab,
            navigationOptions: {
              tabBarIcon: ({ focused}) => {
                let iconName = focused ? 'hexagon-slice-6': 'hexagon-outline';
                let color = focused ? '#2c3c74' : 'gray';
                return <MaterialCommunityIcons name={iconName} size={30} color={color}/>;
              },
            }
        },
        "Défis": {
            screen : ChallengesTab,
            navigationOptions: {
              tabBarIcon: ({ focused}) => {
                let iconName = focused ? 'home': 'home-outline';
                let color = focused ? '#3d9d84' : 'gray';
                return <MaterialCommunityIcons name={iconName} size={30} color={color}/>;
              },
              gesturesEnabled: false,
            }
        },
        "Profil": {
            screen : ProfilTab,
            navigationOptions: {
              tabBarIcon: ({ focused}) => {
                let iconName = focused ? 'user': 'user-o';
                let color = focused ? 'orange' : 'gray';
                return <FontAwesome name={iconName} size={28} color={color}/>;
              },
            }
        },
    },
    {
        tabBarOptions: {
            // activeBackgroundColor: '#DDDDDD', // Couleur d'arrière-plan de l'onglet sélectionné
            inactiveBackgroundColor: '#FFFFFF', // Couleur d'arrière-plan des onglets non sélectionnés
            showLabel: true, // On masque les titres
            showIcon: true,
            activeTintColor: "black",
        }
    }
);

const StackNavigator = createStackNavigator(
    {
        Home: LoginScreen,
        Tabs
    },
    {
        headerMode: 'none',
        navigationOptions: {
          gesturesEnabled: false,
        },
    },
    
  );


const styles = StyleSheet.create({
  icon: {
    width: 30,
    height: 30
  }
})

 

export default createAppContainer(StackNavigator)