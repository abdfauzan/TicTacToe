import React, { useState } from "react";
import {
  Box,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  useMediaQuery,
  Tooltip,
} from "@mui/material";
import { SelectChangeEvent } from "@mui/material";
import { useTheme } from "@mui/system";
import { UUID } from "crypto";

export interface GameMode {
  id: UUID;
  mode: string;
  label: string;
}

export interface AIDifficulty {
  id: UUID;
  value: string;
  label: string;
}

export interface BoardSizeConfiguration {
  id: UUID;
  value: string;
  label: string;
}

export interface TimeLeft {
  id: UUID;
  value: number;
  label: string;
}

interface ConfigurationProps {
  boardSize: number;
  setBoardSize: (size: number) => void;
  gameMode: string;
  setGameMode: (mode:string) => void;
  aiDifficulty: string;
  setAIDifficulty: (
    difficulty: string,
  ) => void;
  boardSizeOptions:BoardSizeConfiguration[];
  setBoardSizeOptions:(configuration:BoardSizeConfiguration[]) => void;
  gameModeOptions:GameMode[];
  setGameModeOptions:(configuration:GameMode[]) => void;
  aiDifficultyOptions:AIDifficulty[];
  setAIDifficultyOptions:(configuration:AIDifficulty[]) => void;
  timeLeft: number;
  setTimeLeft: (size: number) => void;
  timeLeftOptions:TimeLeft[];
  setTimeLeftOptions:(configuration:TimeLeft[]) => void;
  isTimerEnabled: boolean;
  setIsTimerEnabled: (enabled: boolean) => void;
}

const Configuration: React.FC<ConfigurationProps> = ({
  boardSize,
  setBoardSize,
  gameMode,
  setGameMode,
  aiDifficulty,
  setAIDifficulty,
  boardSizeOptions,
  gameModeOptions,
  aiDifficultyOptions,
  timeLeft,
  setTimeLeft,
  timeLeftOptions,
}) => {
  const theme = useTheme();
  const isSmallScreen = useMediaQuery(theme.breakpoints.down("sm"));

  const handleBoardSizeChange = (event: SelectChangeEvent<number>) => {
    const newSize = parseInt(event.target.value as unknown as string, 10);
    if (newSize >= 3) {
      setBoardSize(newSize);
      console.log(event.target.value);
    }
  };

  const handleTimeLeftChange = (event: SelectChangeEvent<number>) => {
    const newSize = parseInt(event.target.value as unknown as string, 10);
    setTimeLeft(newSize);
    console.log(event.target.value);
  };

  const handleGameModeChange = (
    event: SelectChangeEvent<string>,
  ) => {
    setGameMode(event.target.value);
  };

  const isOnlineMode = gameMode === "online";

  return (
    <Box
      sx={{
        textAlign: "center",
        mb: 0,
        p: isSmallScreen ? 2 : 4,
        display: "flex",
        flexDirection: "column",
        gap: 3,
        alignItems: "center",
      }}
    >
      {/* Game Mode Settings */}
      <FormControl sx={{ minWidth: 200 }}>
        <InputLabel id="game-mode-label" sx={{ fontFamily: "Poppins" }}>
          Game Mode
        </InputLabel>
        <Select
          labelId="game-mode-label"
          value={gameMode}
          onChange={handleGameModeChange}
          label="Game Mode"
          variant="outlined"
          sx={{
            fontFamily: "Poppins",
            ".MuiOutlinedInput-notchedOutline": {
              borderColor:
                theme.palette.mode === "dark" ? "#ffffff" : "#000000",
            },
            "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
              borderColor:
                theme.palette.mode === "dark" ? "#ffffff" : "#000000",
            },
            ".MuiSvgIcon-root": {
              color: theme.palette.mode === "dark" ? "#ffffff" : "#000000",
            },
          }}
        >
          {gameModeOptions.map((option) => (
              <MenuItem
                key={option.mode}
                value={option.mode}
                sx={{ fontFamily: "Poppins" }}
              >
                {option.label}
              </MenuItem>
            ))}
        </Select>
      </FormControl>

      {/* Board Size Settings */}
      <Tooltip
        title={
          isOnlineMode
            ? "Board size is fixed at 4x4 in online mode.":""
        }
      >
        <FormControl sx={{ minWidth: 200 }} disabled={isOnlineMode}>
          <InputLabel id="board-size-label" sx={{ fontFamily: "Poppins" }}>
            Board Size
          </InputLabel>
          <Select
            labelId="board-size-label"
            value={boardSize}
            onChange={handleBoardSizeChange}
            label="Board Size"
            variant="outlined"
            sx={{
              fontFamily: "Poppins",
              ".MuiOutlinedInput-notchedOutline": {
                borderColor:
                  theme.palette.mode === "dark" ? "#ffffff" : "#000000",
              },
              "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
                borderColor:
                  theme.palette.mode === "dark" ? "#ffffff" : "#000000",
              },
              ".MuiSvgIcon-root": {
                color: theme.palette.mode === "dark" ? "#ffffff" : "#000000",
              },
            }}
          >
            {boardSizeOptions.map((option) => (
              <MenuItem
                key={option.value}
                value={option.value}
                sx={{ fontFamily: "Poppins" }}
              >
                {option.label}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </Tooltip>

      {/* AI Difficulty Settings */}
      {gameMode === "ai" && (
        <FormControl sx={{ minWidth: 200 }}>
          <InputLabel
            id="ai-difficulty-label"
            sx={{
              fontFamily: "Poppins",
            }}
          >
            AI Difficulty
          </InputLabel>
          <Select
            labelId="ai-difficulty-label"
            value={aiDifficulty}
            onChange={(e) => setAIDifficulty(e.target.value as any)}
            label="AI Difficulty"
            variant="outlined"
            sx={{
              fontFamily: "Poppins",
              ".MuiOutlinedInput-notchedOutline": {
                borderColor:
                  theme.palette.mode === "dark" ? "#ffffff" : "#000000",
              },
              "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
                borderColor:
                  theme.palette.mode === "dark" ? "#ffffff" : "#000000",
              },
              ".MuiSvgIcon-root": {
                color: theme.palette.mode === "dark" ? "#ffffff" : "#000000",
              },
            }}
          >
             {aiDifficultyOptions.map((option) => (
              <MenuItem
                key={option.value}
                value={option.value}
                sx={{ fontFamily: "Poppins" }}
              >
                {option.label}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      )}

        <FormControl sx={{ minWidth: 200 }}>
          <InputLabel
            id="time_left_label"
            sx={{
              fontFamily: "Poppins",
            }}
          >
            Time Duration
          </InputLabel>
          <Select
            labelId="time_left_label"
            value={timeLeft}
            onChange={handleTimeLeftChange}
            label="Time Duration"
            variant="outlined"
            sx={{
              fontFamily: "Poppins",
              ".MuiOutlinedInput-notchedOutline": {
                borderColor:
                  theme.palette.mode === "dark" ? "#ffffff" : "#000000",
              },
              "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
                borderColor:
                  theme.palette.mode === "dark" ? "#ffffff" : "#000000",
              },
              ".MuiSvgIcon-root": {
                color: theme.palette.mode === "dark" ? "#ffffff" : "#000000",
              },
            }}
          >
             {timeLeftOptions.map((option) => (
              <MenuItem
                key={option.value}
                value={option.value}
                sx={{ fontFamily: "Poppins" }}
              >
                {option.label}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

      {/* Timer Settings */}
      <Box
        sx={{
          display: "flex",
          flexDirection: isSmallScreen ? "column" : "row",
          alignItems: "center",
          gap: 2,
        }}
      >
       
      </Box>
    </Box>
  );
};

export default Configuration;
