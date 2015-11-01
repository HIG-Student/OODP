package se.hig.oodp.b9.logic.client;

/**
 * Computer controller
 */
public class AI
{
    /**
     * Client game
     */
    ClientGame game;
    /**
     * Strategy to use
     */
    AIStrategy strategy;

    /**
     * Create computer controller
     * 
     * @param game
     *            the game to act on
     * @param strategy
     *            the strategy to follow
     */
    public AI(ClientGame game, AIStrategy strategy)
    {
        this.game = game;
        setStrategy(strategy);

        game.onTurnStatus.add((ok) ->
        {
            if (!ok)
                return;

            makeMove();
        });
    }

    /**
     * Set strategy
     * 
     * @param strategy
     *            the strategy
     */
    public void setStrategy(AIStrategy strategy)
    {
        this.strategy = strategy;
    }

    /**
     * Make move
     */
    public void makeMove()
    {
        game.makeMove(strategy.makeMove(game, game.me, game.getTable()));
    }

    /**
     * Make move and wait for result
     * 
     * @return was the move ok?
     */
    public boolean makeMoveAndWait()
    {
        return game.makeMoveAndWait(strategy.makeMove(game, game.me, game.getTable()));
    }
}
