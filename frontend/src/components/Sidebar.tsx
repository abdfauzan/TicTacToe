import * as React from 'react';
import { styled, useTheme, Theme, CSSObject } from '@mui/material/styles';
import Box from '@mui/material/Box';
import MuiDrawer from '@mui/material/Drawer';
import MuiAppBar, { AppBarProps as MuiAppBarProps } from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { Link, useLocation, useNavigate } from "react-router-dom";
import {
  Home,
  Leaderboard,
  AccountCircle,
  ExitToApp,
  Login,
  AppRegistration,
  Games,
  AdminPanelSettings
} from "@mui/icons-material";
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import Collapse from '@mui/material/Collapse';

const drawerWidth = 240;

const openedMixin = (theme: Theme): CSSObject => ({
  width: drawerWidth,
  transition: theme.transitions.create('width', {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.enteringScreen,
  }),
  overflowX: 'hidden',
});

const closedMixin = (theme: Theme): CSSObject => ({
  transition: theme.transitions.create('width', {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  overflowX: 'hidden',
  width: `calc(${theme.spacing(7)} + 1px)`,
  [theme.breakpoints.up('sm')]: {
    width: `calc(${theme.spacing(8)} + 1px)`,
  },
});

const DrawerHeader = styled('div')(({ theme }) => ({
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'flex-end',
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
}));

interface AppBarProps extends MuiAppBarProps {
  open?: boolean;
}

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== 'open',
})<AppBarProps>(({ theme }) => ({
  zIndex: theme.zIndex.drawer + 1,
  transition: theme.transitions.create(['width', 'margin'], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  variants: [
    {
      props: ({ open }) => open,
      style: {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
          easing: theme.transitions.easing.sharp,
          duration: theme.transitions.duration.enteringScreen,
        }),
      },
    },
  ],
}));

const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(
  ({ theme }) => ({
    width: drawerWidth,
    flexShrink: 0,
    whiteSpace: 'nowrap',
    boxSizing: 'border-box',
    variants: [
      {
        props: ({ open }) => open,
        style: {
          ...openedMixin(theme),
          '& .MuiDrawer-paper': openedMixin(theme),
        },
      },
      {
        props: ({ open }) => !open,
        style: {
          ...closedMixin(theme),
          '& .MuiDrawer-paper': closedMixin(theme),
        },
      },
    ],
  }),
);

export default function MiniDrawer() {
  const theme = useTheme();
  const [open, setOpen] = React.useState(false);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };
  const [openSubMenu, setOpenSubMenu] = React.useState(true);

  const handleClick = () => {
    setOpenSubMenu(!openSubMenu);
  };

  const isLoggedIn = !!sessionStorage.getItem("token");

  const menuItems = [
    { label: "Home", icon: <Home />, path: "/" },
    ...(isLoggedIn
      ? [{ label: "Game", icon: <Games />, path: "/game" },]
      : []),
  ];

  const adminItems = [
    ...(isLoggedIn
      ? [{ label: "Board Size", icon: <AdminPanelSettings />, path: "/admin/board_size" },
        { label: "Turn Time Duration", icon: <AdminPanelSettings />, path: "/admin/turn_time" },]
      : []),
  ];

  const navigate = useNavigate();

  const handleLogout = (message?: string) => {
    if (message) {
      alert(message);
    }
    sessionStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar position="fixed" open={open}>
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            sx={[
              {
                marginRight: 5,
              },
              open && { display: 'none' },
            ]}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap component="div">
            Tic Tac Toe
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer variant="permanent" open={open}>
        <DrawerHeader>
          <IconButton onClick={handleDrawerClose}>
            {theme.direction === 'rtl' ? <ChevronRightIcon /> : <ChevronLeftIcon />}
          </IconButton>
        </DrawerHeader>
        <Divider />
        <List sx={{ width: 250 }}>
          {menuItems.map((item, index) => (
            <ListItem
              key={index}
              component={Link}
              to={item.path}
              onClick={() => handleDrawerOpen()}
              sx={{
                textDecoration: "none",
                padding: "10px 16px",
              }}
            >
              <ListItemIcon sx={{ color:  "#333" }}>
                {item.icon}
              </ListItemIcon>
              <ListItemText
                primary={item.label}
                primaryTypographyProps={{
                  fontFamily: "Poppins, sans-serif",
                  color: "#333",
                }}
              />
            </ListItem>
          ))}

{isLoggedIn && (<ListItem onClick={handleClick}>
                  <ListItemIcon>
                    <AdminPanelSettings />
                  </ListItemIcon>
                  <ListItemText primary="Admin" />
                  {openSubMenu ? <ExpandLess /> : <ExpandMore />}
                </ListItem>)}
          

                <Collapse in={openSubMenu} timeout="auto" unmountOnExit>
                  <List component="div" disablePadding>
                    
                  {adminItems.map((item, index) => (
                      <ListItem
                        key={index}
                        component={Link}
                        to={item.path}
                        onClick={() => handleDrawerOpen()}
                        sx={{
                          pl: 4 
                        }}
                      >
                        <ListItemIcon sx={{ color:  "#333" }}>
                          {item.icon}
                        </ListItemIcon>
                        <ListItemText
                          primary={item.label}
                          primaryTypographyProps={{
                            fontFamily: "Poppins, sans-serif",
                            color: "#333",
                          }}
                        />
                      </ListItem>
                    ))}
                  </List>
              </Collapse>      

          <ListItem
            component={Link}
            to="/signup"
            onClick={() => handleDrawerOpen()}
            sx={{
              textDecoration: "none",
              padding: "10px 16px",
            }}
          >
            <ListItemIcon sx={{ color: "#333" }}>
              <AppRegistration />
            </ListItemIcon>
            <ListItemText
              primary="Signup"
              primaryTypographyProps={{
                fontFamily: "Poppins, sans-serif",
                color: "#333",
              }}
            />
          </ListItem>
          {isLoggedIn ? (
            <ListItem
              onClick={() => {
                handleLogout();
                handleDrawerOpen();
              }}
              sx={{
                cursor: "pointer",
                padding: "10px 16px",
              }}
            >
              <ListItemIcon sx={{ color:  "#333" }}>
                <ExitToApp />
              </ListItemIcon>
              <ListItemText
                primary="Logout"
                primaryTypographyProps={{
                  fontFamily: "Poppins, sans-serif",
                  color: "#333",
                }}
              />
            </ListItem>
          ) : (
            <ListItem
              component={Link}
              to="/login"
              onClick={() => handleDrawerOpen()}
              sx={{
                textDecoration: "none",
                padding: "10px 16px",
              }}
            >
              <ListItemIcon sx={{ color: "#333" }}>
                <Login />
              </ListItemIcon>
              <ListItemText
                primary="Login"
                primaryTypographyProps={{
                  fontFamily: "Poppins, sans-serif",
                  color: "#333",
                }}
              />
            </ListItem>
          )}
          <ListItem>
            
          </ListItem>
        </List>
      </Drawer>
      
    </Box> 
  );
}