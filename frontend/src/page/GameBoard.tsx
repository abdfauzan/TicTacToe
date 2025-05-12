import React from "react";
import Board from "../components/Board";
import { Box } from "@mui/material";
import { useLocation } from "react-router-dom";

interface GameBoardProps {
  boardSize: number;
  isAI: boolean;
  gameMode: string;
  aiDifficulty: string;
  isTimerEnabled: boolean;
  timeDuration: number;
}

const GameBoard: React.FC = () => {
  
  const location = useLocation();
  return (
    <Box sx={{ textAlign: "center", py: 5 }}>
      
      <Board
        boardSize={location.state.boardSize}
        isAI={location.state.isAI}
        aiDifficulty={location.state.aiDifficulty}
        isTimerEnabled={location.state.isTimerEnabled}
        timeDuration={location.state.timeLeft}
        gameMode={location.state.gameMode}
      />

    </Box>
  );
};

export default GameBoard;
