package se.hig.oodp.b9;

import java.util.List;
import java.util.UUID;

import se.hig.oodp.b9.client.ClientGame;
import se.hig.oodp.b9.client.ClientNetworker;
import se.hig.oodp.b9.server.ServerGame;
import se.hig.oodp.b9.server.ServerNetworker;

public class LocalNetworkingUnit
{
    public ClientNetworker cn;
    public ServerNetworker sn;

    public ServerGame sg;
    public ClientGame cg;

    public LocalNetworkingUnit()
    {
        sg = new ServerGame(sn = new ServerNetworker()
        {
            @Override
            public void sendTable(Table table)
            {
                cg.table = new Table(table.players, table.poolUUID, table.deckUUID);
            }

            @Override
            public void sendPlayerAdded(Player player)
            {
                cg.players.add(new Player(player.getName(), player.getId()));
            }

            @Override
            public void sendMoveCard(Card card, CardCollection collection)
            {
                Card cardy = cg.table.cards.get(card.getId());
                CardCollection cc = cg.table.collections.get(collection.getId());
                cg.table.moveCard(cardy, cc);
            }

            @Override
            public void sendMessageTo(Player source, Player target, String message)
            {
                System.out.println(message);
            }

            @Override
            public void sendMessageTo(Player target, String message)
            {
                System.out.println(message);
            }

            @Override
            public void sendMessage(Player source, String message)
            {
                System.out.println(message);
            }

            @Override
            public void sendMessage(String message)
            {
                System.out.println(message);
            }

            @Override
            public void sendGetMove(Player player)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void sendEndgame(Player target)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void sendCardInfo(Player target, Card info)
            {
                cg.table.cards.get(info.getId()).setCardInfo(info.getCardInfo());
            }

            @Override
            public void sendNewCards(List<UUID> cardIds)
            {
                cg.table.addDeck(cardIds);
            }
        });
        
        cg = new ClientGame(new Player("MrGNU"),cn = new ClientNetworker()
        {
            
            @Override
            public void sendMove(Card card, CardCollection collection)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void sendMessageTo(Player player, String message)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void sendMessage(String message)
            {
                // TODO Auto-generated method stub
                
            }
        });
        
        sg.addPlayer(cg.me);
    }
}
