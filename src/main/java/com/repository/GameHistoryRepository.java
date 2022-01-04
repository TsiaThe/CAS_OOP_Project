package com.repository;

import com.web.GamePhase;
import com.web.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repo with all game phases and actions.
 * @author Theofanis Tsiantas
 * @version  2022.01.03 - version 1
 */
@Repository
public interface GameHistoryRepository extends CrudRepository<GamePhase, Long> {
    List<GamePhase> findByPhaseDescription(String phaseDescription);
}

