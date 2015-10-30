package se.hig.oodp.b9.logic.client;

public class AI
{
    ClientGame game;
    AIStrategy strategy;

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

    public void setStrategy(AIStrategy strategy)
    {
        this.strategy = strategy;
    }

    public void makeMove()
    {
        game.makeMove(strategy.makeMove(game, game.me, game.getTable()));
    }

    public boolean makeMoveAndWait()
    {
        return game.makeMoveAndWait(strategy.makeMove(game, game.me, game.getTable()));
    }
}
