import React from "react";
import {
  Box,
  Typography,
  Button,
} from "@mui/material";
import { Link } from "react-router-dom";
import { useTheme } from "@mui/material/styles";
import { keyframes } from "@emotion/react";

const slideUp = keyframes`
  from {
    transform: translateY(30px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
`;

const LandingPage: React.FC = () => {
  const theme = useTheme();
  const isDarkMode = theme.palette.mode === "dark";

  const themeColor = "#1976d2";

  const isLoggedIn = !!sessionStorage.getItem("token");

  return (
    <Box
      sx={{
        minHeight: "100vh",
        backgroundColor: isDarkMode ? "#1e1e1e" : "#f5f5f5",
        padding: "2rem 0",
        transition: "background-color 0.3s ease",
      }}
    >
    
      <Box
        sx={{
          mt: 6,
          py: 4,
          textAlign: "center",
          backgroundColor: isDarkMode ? "#333" : "#f5f5f5",
          color: isDarkMode ? "red" : "red",
          borderRadius: 2,
          boxShadow: 3,
          animation: `${slideUp} 1s ease-out`,
        }}
      >
        <Typography
          variant="h5"
          sx={{
            mb: 2,
            fontFamily: "Poppins",
            fontWeight: 600,
          }}
        >
          Welcome to Tic Tac Toe Game
        </Typography>
        <Typography
          variant="h5"
          sx={{
            mb: 2,
            fontFamily: "Poppins",
            fontWeight: 600,
          }}
        >
          Ready to Get Started?
        </Typography>
        <Typography
          variant="body1"
          sx={{
            maxWidth: 800,
            mx: "auto",
            fontFamily: "Poppins",
          }}
        >
          Please sign up and login to play the game
        </Typography>
        {isLoggedIn && (<Button
          component={Link}
          to="/game"
          variant="contained"
          sx={{
            mt: 3,
            backgroundColor: themeColor,
            color: "white",
            padding: "0.75rem 1.5rem",
            margin: 3,
            fontFamily: "Poppins",
            fontWeight: 600,
            "&:hover": {
              backgroundColor: "#005bb5",
            },
          }}
        >
         To Game
        </Button>)}
        <Button
          component={Link}
          to="/signup"
          variant="contained"
          sx={{
            mt: 3,
            backgroundColor: themeColor,
            color: "white",
            padding: "0.75rem 1.5rem",
            margin: 3,
            fontFamily: "Poppins",
            fontWeight: 600,
            "&:hover": {
              backgroundColor: "#005bb5",
            },
          }}
        >
          Signup
        </Button>
        <Typography variant="body1" sx={{ mt: 2, fontFamily: "Poppins" }}>
          Already have an account?{" "}
          <Link to="/login" style={{ color: themeColor }}>
            Login
          </Link>
        </Typography>
      </Box>
    </Box>
  );
};

export default LandingPage;
