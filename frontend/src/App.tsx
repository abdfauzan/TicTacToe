import React, { useEffect, useState } from "react";
import { Routes, Route } from "react-router-dom";
import Sidebar from "./components/Sidebar";
import Footer from "./components/Footer";
import Game from "./page/Game";
import GameBoard from "./page/GameBoard";
import Login from "./components/Login";
import Signup from "./components/Signup";
import LandingPage from "./page/LandingPage";
import NotFoundPage from "./page/404Page";
import BoardSizeConfiguration from "./components/BoardSizeConfiguration";
import {
  ThemeProvider,
  createTheme,
  CssBaseline,
  GlobalStyles,
} from "@mui/material";
import { styled} from '@mui/material/styles';
import Box from '@mui/material/Box';
import UpdateBoardSize from "./components/UpdateBoardSize";
import TurnTimeDurationConfiguration from "./components/TurnTimeDurationConfiguration";
import UpdateTurnTimeDuration from "./components/UpdateTurnTimeDuration";

const App: React.FC = () => {
  const [isDarkMode, setDarkMode] = useState<boolean>(() => {
    const savedPreference = localStorage.getItem("isDarkMode");
    return savedPreference ? JSON.parse(savedPreference) : false;
  });

  useEffect(() => {
    const savedPreference = localStorage.getItem("isDarkMode");
    if (savedPreference) {
      setDarkMode(JSON.parse(savedPreference));
    }
  }, []);

  // Create the theme based on dark mode state
  const theme = createTheme({
    palette: {
      mode: isDarkMode ? "dark" : "light",
      primary: {
        main: "#1976d2",
      },
      background: {
        default: isDarkMode ? "#121212" : "#f5f5f5",
        paper: isDarkMode ? "#1e1e1e" : "#ffffff",
      },
      text: {
        primary: isDarkMode ? "#ffffff" : "#000000",
        secondary: isDarkMode ? "#aaaaaa" : "#666666",
      },
    },
    typography: {
      fontFamily: "Poppins, sans-serif",
    },
  });

  const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
  }));

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      {/* Smooth transition effect */}
      <GlobalStyles
        styles={{
          "body, #root": {
            transition: "background-color 0.3s ease",
          },
        }}
      />
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          minHeight: "100vh",
        }}
      >
        <Sidebar />
        <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
          <DrawerHeader />
          <Routes>  
            <Route path="/" element={<LandingPage />} />
            <Route path="/game" element={<Game />} />
            <Route path="/gameboard" element={<GameBoard />} />
            <Route path="/admin/board_size" element={<BoardSizeConfiguration />} />
            <Route path="/admin/board_size/:id" element={<UpdateBoardSize />} />
            <Route path="/admin/board_size/add" element={<UpdateBoardSize />} />
            <Route path="/admin/turn_time" element={<TurnTimeDurationConfiguration />} />
            <Route path="/admin/turn_time/:id" element={<UpdateTurnTimeDuration />} />
            <Route path="/admin/turn_time/add" element={<UpdateTurnTimeDuration />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/landing" element={<LandingPage />} />
            <Route path="*" element={<NotFoundPage />} />
          </Routes>
          <Footer />
        </Box>
      </div>
    </ThemeProvider>
  );
};

export default App;
