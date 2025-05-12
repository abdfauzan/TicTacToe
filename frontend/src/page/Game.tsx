import React, { useState, useEffect } from "react";
import Configuration, { TimeLeft } from "../components/Configuration";
import { Box, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import {BoardSizeConfiguration,AIDifficulty,GameMode} from "../components/Configuration"

const boardSizeConfigurations: BoardSizeConfiguration[] = []
const aiDifficultyConfigurations: AIDifficulty[] = []
const gameModeConfigurations: GameMode[] = []
const timeLeftConfigurations: TimeLeft[] = []

const Game: React.FC = () => {
  const [boardSize, setBoardSize] = useState(5);
  const [gameModeOptions, setGameModeOptions] = useState<GameMode[]>(gameModeConfigurations);
  const [aiDifficultyOptions, setAIDifficultyOptions] = useState<AIDifficulty[]>(aiDifficultyConfigurations);
  const [gameMode, setGameMode] = useState<string>("ai");
  const [aiDifficulty, setAIDifficulty] = useState<string>("hard");
  const [boardSizeOptions, setBoardSizeOptions] = useState<BoardSizeConfiguration[]>(boardSizeConfigurations);
  const [isTimerEnabled, setIsTimerEnabled] = useState(false);
  const [timeLeft, setTimeLeft] = useState(30);
  const [timeLeftOptions, setTimeLeftOptions] = useState<TimeLeft[]>(timeLeftConfigurations);
  const navigate = useNavigate();

  const gameBoard = () => {
    // If game is over or board is empty, reset the board
    navigate("/gameboard",{state:{boardSize:boardSize, isAI: gameMode==="ai",
      aiDifficulty:aiDifficulty,isTimerEnabled:isTimerEnabled,timeLeft:timeLeft,
      gameMode:gameMode
    }});
  };
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  
  const token = sessionStorage.getItem('token'); // Retrieve token from storage

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:8080/tictactoe_game_service/client/configuration', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`, // Include the authorization header
            'Content-Type': 'application/json'
          },
        });

        console.log("Login successful:", response.ok);

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const json = await response.json();
        setBoardSizeOptions(json.boardSizeConfiguration);
        setAIDifficultyOptions(json.aiDifficulties);
        setGameModeOptions(json.gameMode);
        setTimeLeftOptions(json.turnTimeDurationConfiguration);
      } catch (error) {
        console.error("Fetching error:", error);
      }
    };

    if (token) {
      fetchData();
    } else {
      // Handle the case where there is no token, e.g., redirect to login
      console.warn('No token available, cannot fetch data.');
    }
  }, [token]); // Effect runs when token changes
  

  return (
    <Box sx={{ textAlign: "center", py: 5 }}>
      <Typography
        variant="h4"
        gutterBottom
        sx={{ fontFamily: "Poppins", mb: 2, fontWeight: "bold" }}
      >
        TicTacToe Game
      </Typography>
      {/* Divider */}
      <div
        style={{
          width: "100%",
          borderBottom: "1px solid #e0e0e0",
          margin: "0 auto 20px",
        }}
      />
      <Typography
        variant="h6"
        gutterBottom
        sx={{ fontFamily: "Poppins", mb: 0 }}
      >
        Configuration
      </Typography>
      <Configuration
        boardSize={boardSize}
        setBoardSize={setBoardSize}
        gameMode={gameMode}
        setGameMode={setGameMode}
        aiDifficulty={aiDifficulty}
        setAIDifficulty={setAIDifficulty}
        boardSizeOptions={boardSizeOptions}
        setBoardSizeOptions={setBoardSizeOptions}
        isTimerEnabled={isTimerEnabled}
        setIsTimerEnabled={setIsTimerEnabled}
        timeLeft={timeLeft}
        setTimeLeft={setTimeLeft}
        gameModeOptions={gameModeOptions}
        setGameModeOptions={setGameModeOptions}
        aiDifficultyOptions={aiDifficultyOptions}
        setAIDifficultyOptions={setAIDifficultyOptions}
        timeLeftOptions={timeLeftOptions}
        setTimeLeftOptions={setTimeLeftOptions}
      />
      {/* Divider */}
      <div
        style={{
          width: "100%",
          borderBottom: "1px solid #e0e0e0",
          margin: "0 auto 20px",
        }}
      />
          <Button
                  variant="contained"
                  color="primary"
                  onClick={gameBoard}
                  sx={{ margin: 2, fontFamily: "Poppins" }}
                >
                  Play Game
                </Button>

    
    </Box>
  );
};

export default Game;
