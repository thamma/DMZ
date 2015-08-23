package me.thamma.DMZ.core;

import java.util.List;

import org.bukkit.entity.Player;

import me.thamma.DMZ.utils.Argument;
import me.thamma.DMZ.utils.CommandHandler;

public class Tester {

	public static void main(String[] args0) {
		String[] args = "meh \"haha  haushdfuiahsd ad\" 1".split(" ");
		CommandHandler ch = new CommandHandler(null, "test", args).add(new Argument() {

			@Override
			public String name() {
				return "bla";
			}

			@Override
			public String descr() {
				return "bla";
			}

			@Override
			public void run(Player p, List<String> args) {
				System.out.println("bla called");
			}

			@Override
			public Class<?>[] pattern() {
				return new Class<?>[] { String.class, Integer.class };
			}

		}).add(new Argument() {

			@Override
			public String name() {
				return "meh";
			}

			@Override
			public String descr() {
				return "meh1";
			}

			@Override
			public void run(Player p, List<String> args) {
				// TODO Auto-generated method stub
				System.out.println("meh called");
			}

			@Override
			public Class<?>[] pattern() {
				return new Class<?>[] { Boolean.class };
			}

		}).add(new Argument() {

			@Override
			public String name() {
				return "meh";
			}

			@Override
			public String descr() {
				return "meh2";
			}

			@Override
			public void run(Player p, List<String> args) {
				// TODO Auto-generated method stub
				System.out.println("meh called");
			}

			@Override
			public Class<?>[] pattern() {
				return new Class<?>[] { Boolean.class, Integer.class };
			}

		});
		ch.perform();
	}

}
