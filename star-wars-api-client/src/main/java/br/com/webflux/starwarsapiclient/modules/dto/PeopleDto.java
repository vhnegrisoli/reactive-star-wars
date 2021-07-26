package br.com.webflux.starwarsapiclient.modules.dto;

import lombok.Data;

import java.util.List;

@Data
public class PeopleDto {

    public List<Object> results;
}
