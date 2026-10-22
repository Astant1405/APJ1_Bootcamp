package ru.school21.TicTacToe.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.school21.TicTacToe.domain.model.Game;
import ru.school21.TicTacToe.domain.service.GameEngine;
import ru.school21.TicTacToe.enums.GameStateEnum;
import ru.school21.TicTacToe.web.mapper.GameMapper;

import java.util.UUID;

@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class TicTacToeController {

    private final GameEngine gameService;
    private final GameMapper gameMapper;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/create")
    public String createGame() {
        var game = gameService.createNewGame();
        return "redirect:/game/" + game.getId();
    }

    @GetMapping("/{gameId}")
    public String showGame(@PathVariable UUID gameId, Model model) {
        var game = gameService.getGame(gameId);
        model.addAttribute("game", gameMapper.toResponse(game));
        model.addAttribute("gameId", gameId);
        if (!GameStateEnum.getInProgress().equals(game.getStatus())) {
            return "final";
        }
        return "game";
    }

    @PostMapping("/{gameId}/move")
    public String makeMove(@PathVariable UUID gameId,
                           @RequestParam("move") String move,
                           RedirectAttributes redirectAttributes) {
        String[] parts = move.split(",");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);

        try {
            Game game = gameService.getGame(gameId);
            int[][] currentBoard = gameMapper.deserializeBoard(game.getFieldState());

            if (currentBoard[row][col] != 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "Ячейка уже занята!");
                return "redirect:/game/" + gameId;
            }
            currentBoard[row][col] = 1;
            gameService.makeMoveWithFullBoard(gameId, currentBoard);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/game/" + gameId;
    }
}