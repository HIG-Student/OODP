package se.hig.oodp.b9;

import java.io.Serializable;

public class Package<T extends Serializable> implements Serializable
{
    public enum Type
    {
        Table, Cards, CardInfo, Move, PlayerAdded, Message, RequestMove, Close , ServerInfo , MoveResult
    }

    public Type type;

    public T value;

    public Package(T value, Type type)
    {
        this.value = value;
        this.type = type;
    }
}
