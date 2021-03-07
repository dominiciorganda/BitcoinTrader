package com.example.demo.Controllers;

import com.example.demo.DTOs.CoinDTO;
import com.example.demo.Mappers.CoinMapper;
import com.example.demo.Services.ElrondService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Api
@RestController
@RequestMapping("/CoinTrader/elrond")
public class ElrondController {
    public ElrondController() throws IOException {
    }

    private ElrondService elrondService = new ElrondService();

    @GetMapping("/getLastMonth")
    public List<CoinDTO> getLastMonth() {
        return CoinMapper.mapCoinListtoCoinDTOList(elrondService.getLastMonth());
    }

    @GetMapping("/getMax")
    public CoinDTO getMax() {
        return CoinMapper.mapCointoCoinDTO(elrondService.getAllTimeMax());
    }

    @GetMapping("/getLast")
    public CoinDTO getLast() {
        return CoinMapper.mapCointoCoinDTO(elrondService.getLast());
    }

    @GetMapping("/getLastX/{number}")
    public List<CoinDTO> getLastX(@PathVariable("number") int num) {
        return CoinMapper.mapCoinListtoCoinDTOList(elrondService.getLastX(num));
    }

    @GetMapping(value = "/getActual")
    public CoinDTO getActual() throws IOException {
        return CoinMapper.mapCointoCoinDTO(elrondService.getActual());
    }

}
