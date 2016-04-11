package com.github.dylon.liblevenshtein.levenshtein;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.val;

import com.github.dylon.liblevenshtein.levenshtein.distance.MemoizedMergeAndSplit;
import com.github.dylon.liblevenshtein.levenshtein.factory.TransducerBuilder;
import com.github.dylon.liblevenshtein.serialization.BytecodeSerializer;
import com.github.dylon.liblevenshtein.serialization.ProtobufSerializer;
import com.github.dylon.liblevenshtein.serialization.Serializer;
import static com.github.dylon.liblevenshtein.assertion.CandidateAssertions.assertThat;
import static com.github.dylon.liblevenshtein.assertion.SetAssertions.assertThat;

@SuppressWarnings("unchecked")
public class MergeAndSplitTransducerTest extends AbstractTransducerTest {
  private static final int MAX_DISTANCE = 3;
  private static final String QUERY_TERM = "Jvaa";

  private ITransducer<Candidate> transducer;
  private Set<Candidate> expectedCandidates;

  @BeforeTest
  @SuppressWarnings("checkstyle:methodlength")
  public void setUp() throws IOException {
    try (final InputStream istream =
        getClass().getResourceAsStream("/programming-languages.txt")) {

      final Collection<String> dictionary = readLines(istream);

      this.transducer = new TransducerBuilder()
        .algorithm(Algorithm.MERGE_AND_SPLIT)
        .defaultMaxDistance(MAX_DISTANCE)
        .dictionary(dictionary, true)
        .build();
    }

    this.expectedCandidates = new HashSet<>();
    expectedCandidates.add(new Candidate("A#", 2));
    expectedCandidates.add(new Candidate("A+", 2));
    expectedCandidates.add(new Candidate("Ada", 2));
    expectedCandidates.add(new Candidate("Agda", 2));
    expectedCandidates.add(new Candidate("Bash", 2));
    expectedCandidates.add(new Candidate("bc", 2));
    expectedCandidates.add(new Candidate("C#", 2));
    expectedCandidates.add(new Candidate("Caml", 2));
    expectedCandidates.add(new Candidate("Cg", 2));
    expectedCandidates.add(new Candidate("Ch", 2));
    expectedCandidates.add(new Candidate("CL", 2));
    expectedCandidates.add(new Candidate("Cola", 2));
    expectedCandidates.add(new Candidate("Dart", 2));
    expectedCandidates.add(new Candidate("dc", 2));
    expectedCandidates.add(new Candidate("E#", 2));
    expectedCandidates.add(new Candidate("Ease", 2));
    expectedCandidates.add(new Candidate("es", 2));
    expectedCandidates.add(new Candidate("F#", 2));
    expectedCandidates.add(new Candidate("FL", 2));
    expectedCandidates.add(new Candidate("FP", 2));
    expectedCandidates.add(new Candidate("GJ", 2));
    expectedCandidates.add(new Candidate("GM", 2));
    expectedCandidates.add(new Candidate("Go", 2));
    expectedCandidates.add(new Candidate("Haxe", 2));
    expectedCandidates.add(new Candidate("Id", 2));
    expectedCandidates.add(new Candidate("Io", 2));
    expectedCandidates.add(new Candidate("J#", 2));
    expectedCandidates.add(new Candidate("J++", 2));
    expectedCandidates.add(new Candidate("JADE", 2));
    expectedCandidates.add(new Candidate("Jako", 2));
    expectedCandidates.add(new Candidate("JAL", 2));
    expectedCandidates.add(new Candidate("JASS", 2));
    expectedCandidates.add(new Candidate("Java", 2));
    expectedCandidates.add(new Candidate("JCL", 2));
    expectedCandidates.add(new Candidate("JEAN", 2));
    expectedCandidates.add(new Candidate("JOSS", 2));
    expectedCandidates.add(new Candidate("Joy", 2));
    expectedCandidates.add(new Candidate("Julia", 2));
    expectedCandidates.add(new Candidate("Lava", 2));
    expectedCandidates.add(new Candidate("Leda", 2));
    expectedCandidates.add(new Candidate("Lua", 2));
    expectedCandidates.add(new Candidate("M4", 2));
    expectedCandidates.add(new Candidate("make", 2));
    expectedCandidates.add(new Candidate("Mary", 2));
    expectedCandidates.add(new Candidate("Max", 2));
    expectedCandidates.add(new Candidate("Maya", 2));
    expectedCandidates.add(new Candidate("Mesa", 2));
    expectedCandidates.add(new Candidate("ML", 2));
    expectedCandidates.add(new Candidate("Nu", 2));
    expectedCandidates.add(new Candidate("Oak", 2));
    expectedCandidates.add(new Candidate("Opa", 2));
    expectedCandidates.add(new Candidate("Oz", 2));
    expectedCandidates.add(new Candidate("P#", 2));
    expectedCandidates.add(new Candidate("Pawn", 2));
    expectedCandidates.add(new Candidate("Qalb", 2));
    expectedCandidates.add(new Candidate("Qi", 2));
    expectedCandidates.add(new Candidate("rc", 2));
    expectedCandidates.add(new Candidate("Reia", 2));
    expectedCandidates.add(new Candidate("S2", 2));
    expectedCandidates.add(new Candidate("S3", 2));
    expectedCandidates.add(new Candidate("SR", 2));
    expectedCandidates.add(new Candidate("Tea", 2));
    expectedCandidates.add(new Candidate("Vala", 2));
    expectedCandidates.add(new Candidate("Vvvv", 2));
    expectedCandidates.add(new Candidate("X#", 2));
    expectedCandidates.add(new Candidate("XC", 2));
    expectedCandidates.add(new Candidate("XL", 2));
    expectedCandidates.add(new Candidate("A++", 3));
    expectedCandidates.add(new Candidate("ABAP", 3));
    expectedCandidates.add(new Candidate("ABC", 3));
    expectedCandidates.add(new Candidate("ABLE", 3));
    expectedCandidates.add(new Candidate("ABSET", 3));
    expectedCandidates.add(new Candidate("ABSYS", 3));
    expectedCandidates.add(new Candidate("ACC", 3));
    expectedCandidates.add(new Candidate("ACL2", 3));
    expectedCandidates.add(new Candidate("Agora", 3));
    expectedCandidates.add(new Candidate("AIMMS", 3));
    expectedCandidates.add(new Candidate("Alef", 3));
    expectedCandidates.add(new Candidate("ALF", 3));
    expectedCandidates.add(new Candidate("Alice", 3));
    expectedCandidates.add(new Candidate("Alma-0", 3));
    expectedCandidates.add(new Candidate("Amiga E", 3));
    expectedCandidates.add(new Candidate("AMOS", 3));
    expectedCandidates.add(new Candidate("AMPL", 3));
    expectedCandidates.add(new Candidate("APL", 3));
    expectedCandidates.add(new Candidate("Arc", 3));
    expectedCandidates.add(new Candidate("ARexx", 3));
    expectedCandidates.add(new Candidate("Argus", 3));
    expectedCandidates.add(new Candidate("ATS", 3));
    expectedCandidates.add(new Candidate("AWK", 3));
    expectedCandidates.add(new Candidate("Axum", 3));
    expectedCandidates.add(new Candidate("B", 3));
    expectedCandidates.add(new Candidate("Babbage", 3));
    expectedCandidates.add(new Candidate("BAIL", 3));
    expectedCandidates.add(new Candidate("BASIC", 3));
    expectedCandidates.add(new Candidate("Batch", 3));
    expectedCandidates.add(new Candidate("BCPL", 3));
    expectedCandidates.add(new Candidate("BETA", 3));
    expectedCandidates.add(new Candidate("BitC", 3));
    expectedCandidates.add(new Candidate("BLISS", 3));
    expectedCandidates.add(new Candidate("Blue", 3));
    expectedCandidates.add(new Candidate("Bon", 3));
    expectedCandidates.add(new Candidate("Boo", 3));
    expectedCandidates.add(new Candidate("BPEL", 3));
    expectedCandidates.add(new Candidate("BREW", 3));
    expectedCandidates.add(new Candidate("C", 3));
    expectedCandidates.add(new Candidate("C--", 3));
    expectedCandidates.add(new Candidate("C/AL", 3));
    expectedCandidates.add(new Candidate("C++", 3));
    expectedCandidates.add(new Candidate("CDuce", 3));
    expectedCandidates.add(new Candidate("Cecil", 3));
    expectedCandidates.add(new Candidate("Cel", 3));
    expectedCandidates.add(new Candidate("Cesil", 3));
    expectedCandidates.add(new Candidate("CFML", 3));
    expectedCandidates.add(new Candidate("CHAIN", 3));
    expectedCandidates.add(new Candidate("Charm", 3));
    expectedCandidates.add(new Candidate("Chef", 3));
    expectedCandidates.add(new Candidate("CHILL", 3));
    expectedCandidates.add(new Candidate("ChucK", 3));
    expectedCandidates.add(new Candidate("CICS", 3));
    expectedCandidates.add(new Candidate("Cilk", 3));
    expectedCandidates.add(new Candidate("Clean", 3));
    expectedCandidates.add(new Candidate("CLIST", 3));
    expectedCandidates.add(new Candidate("CLU", 3));
    expectedCandidates.add(new Candidate("CMS-2", 3));
    expectedCandidates.add(new Candidate("COBOL", 3));
    expectedCandidates.add(new Candidate("Cobra", 3));
    expectedCandidates.add(new Candidate("CODE", 3));
    expectedCandidates.add(new Candidate("ColdC", 3));
    expectedCandidates.add(new Candidate("COMAL", 3));
    expectedCandidates.add(new Candidate("COMIT", 3));
    expectedCandidates.add(new Candidate("Cool", 3));
    expectedCandidates.add(new Candidate("Coq", 3));
    expectedCandidates.add(new Candidate("Corn", 3));
    expectedCandidates.add(new Candidate("CPL", 3));
    expectedCandidates.add(new Candidate("csh", 3));
    expectedCandidates.add(new Candidate("CSP", 3));
    expectedCandidates.add(new Candidate("Curl", 3));
    expectedCandidates.add(new Candidate("Curry", 3));
    expectedCandidates.add(new Candidate("D", 3));
    expectedCandidates.add(new Candidate("DASL", 3));
    expectedCandidates.add(new Candidate("dBase", 3));
    expectedCandidates.add(new Candidate("DCL", 3));
    expectedCandidates.add(new Candidate("DIBOL", 3));
    expectedCandidates.add(new Candidate("DinkC", 3));
    expectedCandidates.add(new Candidate("Dog", 3));
    expectedCandidates.add(new Candidate("Draco", 3));
    expectedCandidates.add(new Candidate("Dylan", 3));
    expectedCandidates.add(new Candidate("E", 3));
    expectedCandidates.add(new Candidate("EGL", 3));
    expectedCandidates.add(new Candidate("ELAN", 3));
    expectedCandidates.add(new Candidate("Elm", 3));
    expectedCandidates.add(new Candidate("Emerald", 3));
    expectedCandidates.add(new Candidate("Erlang", 3));
    expectedCandidates.add(new Candidate("ESPOL", 3));
    expectedCandidates.add(new Candidate("Etoys", 3));
    expectedCandidates.add(new Candidate("Euler", 3));
    expectedCandidates.add(new Candidate("F", 3));
    expectedCandidates.add(new Candidate("Fancy", 3));
    expectedCandidates.add(new Candidate("FAUST", 3));
    expectedCandidates.add(new Candidate("Felix", 3));
    expectedCandidates.add(new Candidate("FFP", 3));
    expectedCandidates.add(new Candidate("Flex", 3));
    expectedCandidates.add(new Candidate("FOCAL", 3));
    expectedCandidates.add(new Candidate("FOCUS", 3));
    expectedCandidates.add(new Candidate("FOIL", 3));
    expectedCandidates.add(new Candidate("Forth", 3));
    expectedCandidates.add(new Candidate("FoxBase", 3));
    expectedCandidates.add(new Candidate("FPr", 3));
    expectedCandidates.add(new Candidate("G", 3));
    expectedCandidates.add(new Candidate("GAMS", 3));
    expectedCandidates.add(new Candidate("GAP", 3));
    expectedCandidates.add(new Candidate("GDL", 3));
    expectedCandidates.add(new Candidate("Genie", 3));
    expectedCandidates.add(new Candidate("Gibiane", 3));
    expectedCandidates.add(new Candidate("GLSL", 3));
    expectedCandidates.add(new Candidate("GNU E", 3));
    expectedCandidates.add(new Candidate("Go!", 3));
    expectedCandidates.add(new Candidate("GOAL", 3));
    expectedCandidates.add(new Candidate("Gödel", 3));
    expectedCandidates.add(new Candidate("Godiva", 3));
    expectedCandidates.add(new Candidate("GOM", 3));
    expectedCandidates.add(new Candidate("Goo", 3));
    expectedCandidates.add(new Candidate("Gosu", 3));
    expectedCandidates.add(new Candidate("GPSS", 3));
    expectedCandidates.add(new Candidate("GRASS", 3));
    expectedCandidates.add(new Candidate("HAL/S", 3));
    expectedCandidates.add(new Candidate("HLSL", 3));
    expectedCandidates.add(new Candidate("Hop", 3));
    expectedCandidates.add(new Candidate("Hope", 3));
    expectedCandidates.add(new Candidate("Hugo", 3));
    expectedCandidates.add(new Candidate("Hume", 3));
    expectedCandidates.add(new Candidate("ICI", 3));
    expectedCandidates.add(new Candidate("Icon", 3));
    expectedCandidates.add(new Candidate("IDL", 3));
    expectedCandidates.add(new Candidate("Idris", 3));
    expectedCandidates.add(new Candidate("IMP", 3));
    expectedCandidates.add(new Candidate("Ioke", 3));
    expectedCandidates.add(new Candidate("IPL", 3));
    expectedCandidates.add(new Candidate("ISPF", 3));
    expectedCandidates.add(new Candidate("ISWIM", 3));
    expectedCandidates.add(new Candidate("J", 3));
    expectedCandidates.add(new Candidate("Janus", 3));
    expectedCandidates.add(new Candidate("Joule", 3));
    expectedCandidates.add(new Candidate("JOVIAL", 3));
    expectedCandidates.add(new Candidate("JScript", 3));
    expectedCandidates.add(new Candidate("K", 3));
    expectedCandidates.add(new Candidate("Karel", 3));
    expectedCandidates.add(new Candidate("KEE", 3));
    expectedCandidates.add(new Candidate("KIF", 3));
    expectedCandidates.add(new Candidate("Kojo", 3));
    expectedCandidates.add(new Candidate("KRC", 3));
    expectedCandidates.add(new Candidate("KRL", 3));
    expectedCandidates.add(new Candidate("ksh", 3));
    expectedCandidates.add(new Candidate("L", 3));
    expectedCandidates.add(new Candidate("Lagoona", 3));
    expectedCandidates.add(new Candidate("LANSA", 3));
    expectedCandidates.add(new Candidate("Lasso", 3));
    expectedCandidates.add(new Candidate("LaTeX", 3));
    expectedCandidates.add(new Candidate("LC-3", 3));
    expectedCandidates.add(new Candidate("LIL", 3));
    expectedCandidates.add(new Candidate("Limbo", 3));
    expectedCandidates.add(new Candidate("LINC", 3));
    expectedCandidates.add(new Candidate("Lingo", 3));
    expectedCandidates.add(new Candidate("LIS", 3));
    expectedCandidates.add(new Candidate("LISA", 3));
    expectedCandidates.add(new Candidate("Lisaac", 3));
    expectedCandidates.add(new Candidate("Lisp", 3));
    expectedCandidates.add(new Candidate("Lithe", 3));
    expectedCandidates.add(new Candidate("Logo", 3));
    expectedCandidates.add(new Candidate("Logtalk", 3));
    expectedCandidates.add(new Candidate("LPC", 3));
    expectedCandidates.add(new Candidate("LSE", 3));
    expectedCandidates.add(new Candidate("LSL", 3));
    expectedCandidates.add(new Candidate("Lucid", 3));
    expectedCandidates.add(new Candidate("Lynx", 3));
    expectedCandidates.add(new Candidate("M", 3));
    expectedCandidates.add(new Candidate("M2001", 3));
    expectedCandidates.add(new Candidate("MAD", 3));
    expectedCandidates.add(new Candidate("MAD/I", 3));
    expectedCandidates.add(new Candidate("Magik", 3));
    expectedCandidates.add(new Candidate("Magma", 3));
    expectedCandidates.add(new Candidate("Maple", 3));
    expectedCandidates.add(new Candidate("Maxima", 3));
    expectedCandidates.add(new Candidate("MDL", 3));
    expectedCandidates.add(new Candidate("MetaL", 3));
    expectedCandidates.add(new Candidate("MIIS", 3));
    expectedCandidates.add(new Candidate("MIMIC", 3));
    expectedCandidates.add(new Candidate("Mirah", 3));
    expectedCandidates.add(new Candidate("Miranda", 3));
    expectedCandidates.add(new Candidate("Moby", 3));
    expectedCandidates.add(new Candidate("Modula", 3));
    expectedCandidates.add(new Candidate("Mohol", 3));
    expectedCandidates.add(new Candidate("MOO", 3));
    expectedCandidates.add(new Candidate("Mouse", 3));
    expectedCandidates.add(new Candidate("MPD", 3));
    expectedCandidates.add(new Candidate("MSIL", 3));
    expectedCandidates.add(new Candidate("MSL", 3));
    expectedCandidates.add(new Candidate("MUMPS", 3));
    expectedCandidates.add(new Candidate("NASM", 3));
    expectedCandidates.add(new Candidate("Neko", 3));
    expectedCandidates.add(new Candidate("nesC", 3));
    expectedCandidates.add(new Candidate("NESL", 3));
    expectedCandidates.add(new Candidate("NEWP", 3));
    expectedCandidates.add(new Candidate("NGL", 3));
    expectedCandidates.add(new Candidate("Nial", 3));
    expectedCandidates.add(new Candidate("Nice", 3));
    expectedCandidates.add(new Candidate("NPL", 3));
    expectedCandidates.add(new Candidate("NSIS", 3));
    expectedCandidates.add(new Candidate("o:XML", 3));
    expectedCandidates.add(new Candidate("Obix", 3));
    expectedCandidates.add(new Candidate("OBJ2", 3));
    expectedCandidates.add(new Candidate("Obliq", 3));
    expectedCandidates.add(new Candidate("Obol", 3));
    expectedCandidates.add(new Candidate("OCaml", 3));
    expectedCandidates.add(new Candidate("occam", 3));
    expectedCandidates.add(new Candidate("Octave", 3));
    expectedCandidates.add(new Candidate("Onyx", 3));
    expectedCandidates.add(new Candidate("Opal", 3));
    expectedCandidates.add(new Candidate("OPL", 3));
    expectedCandidates.add(new Candidate("OPS5", 3));
    expectedCandidates.add(new Candidate("Orc", 3));
    expectedCandidates.add(new Candidate("Oriel", 3));
    expectedCandidates.add(new Candidate("Pascal", 3));
    expectedCandidates.add(new Candidate("PCF", 3));
    expectedCandidates.add(new Candidate("PDL", 3));
    expectedCandidates.add(new Candidate("PEARL", 3));
    expectedCandidates.add(new Candidate("Perl", 3));
    expectedCandidates.add(new Candidate("PHP", 3));
    expectedCandidates.add(new Candidate("Pico", 3));
    expectedCandidates.add(new Candidate("Pict", 3));
    expectedCandidates.add(new Candidate("Pike", 3));
    expectedCandidates.add(new Candidate("PIKT", 3));
    expectedCandidates.add(new Candidate("PILOT", 3));
    expectedCandidates.add(new Candidate("Pizza", 3));
    expectedCandidates.add(new Candidate("PL-11", 3));
    expectedCandidates.add(new Candidate("PL/0", 3));
    expectedCandidates.add(new Candidate("PL/B", 3));
    expectedCandidates.add(new Candidate("PL/C", 3));
    expectedCandidates.add(new Candidate("PL/I", 3));
    expectedCandidates.add(new Candidate("PL/M", 3));
    expectedCandidates.add(new Candidate("PL/P", 3));
    expectedCandidates.add(new Candidate("PL360", 3));
    expectedCandidates.add(new Candidate("PLANC", 3));
    expectedCandidates.add(new Candidate("PLEX", 3));
    expectedCandidates.add(new Candidate("Plus", 3));
    expectedCandidates.add(new Candidate("PPL", 3));
    expectedCandidates.add(new Candidate("Pro*C", 3));
    expectedCandidates.add(new Candidate("PROIV", 3));
    expectedCandidates.add(new Candidate("Promela", 3));
    expectedCandidates.add(new Candidate("Pure", 3));
    expectedCandidates.add(new Candidate("Q", 3));
    expectedCandidates.add(new Candidate("QPL", 3));
    expectedCandidates.add(new Candidate("R", 3));
    expectedCandidates.add(new Candidate("R++", 3));
    expectedCandidates.add(new Candidate("RAPID", 3));
    expectedCandidates.add(new Candidate("Rapira", 3));
    expectedCandidates.add(new Candidate("REBOL", 3));
    expectedCandidates.add(new Candidate("Red", 3));
    expectedCandidates.add(new Candidate("REFAL", 3));
    expectedCandidates.add(new Candidate("rex", 3));
    expectedCandidates.add(new Candidate("REXX", 3));
    expectedCandidates.add(new Candidate("Rlab", 3));
    expectedCandidates.add(new Candidate("ROOP", 3));
    expectedCandidates.add(new Candidate("RPG", 3));
    expectedCandidates.add(new Candidate("RPL", 3));
    expectedCandidates.add(new Candidate("RSL", 3));
    expectedCandidates.add(new Candidate("RTL/2", 3));
    expectedCandidates.add(new Candidate("Ruby", 3));
    expectedCandidates.add(new Candidate("Rust", 3));
    expectedCandidates.add(new Candidate("S", 3));
    expectedCandidates.add(new Candidate("S-Lang", 3));
    expectedCandidates.add(new Candidate("S/SL", 3));
    expectedCandidates.add(new Candidate("SA-C", 3));
    expectedCandidates.add(new Candidate("SAIL", 3));
    expectedCandidates.add(new Candidate("SALSA", 3));
    expectedCandidates.add(new Candidate("SAM76", 3));
    expectedCandidates.add(new Candidate("SAS", 3));
    expectedCandidates.add(new Candidate("SASL", 3));
    expectedCandidates.add(new Candidate("Sawzall", 3));
    expectedCandidates.add(new Candidate("SBL", 3));
    expectedCandidates.add(new Candidate("Scala", 3));
    expectedCandidates.add(new Candidate("Scilab", 3));
    expectedCandidates.add(new Candidate("Sed", 3));
    expectedCandidates.add(new Candidate("Seed7", 3));
    expectedCandidates.add(new Candidate("Self", 3));
    expectedCandidates.add(new Candidate("SETL", 3));
    expectedCandidates.add(new Candidate("Simula", 3));
    expectedCandidates.add(new Candidate("SISAL", 3));
    expectedCandidates.add(new Candidate("SLIP", 3));
    expectedCandidates.add(new Candidate("SMALL", 3));
    expectedCandidates.add(new Candidate("SML", 3));
    expectedCandidates.add(new Candidate("SOL", 3));
    expectedCandidates.add(new Candidate("SP/k", 3));
    expectedCandidates.add(new Candidate("Span", 3));
    expectedCandidates.add(new Candidate("SPARK", 3));
    expectedCandidates.add(new Candidate("SPIN", 3));
    expectedCandidates.add(new Candidate("SPS", 3));
    expectedCandidates.add(new Candidate("Squeak", 3));
    expectedCandidates.add(new Candidate("Stata", 3));
    expectedCandidates.add(new Candidate("Strand", 3));
    expectedCandidates.add(new Candidate("SYMPL", 3));
    expectedCandidates.add(new Candidate("T", 3));
    expectedCandidates.add(new Candidate("T-SQL", 3));
    expectedCandidates.add(new Candidate("TACL", 3));
    expectedCandidates.add(new Candidate("TADS", 3));
    expectedCandidates.add(new Candidate("TAL", 3));
    expectedCandidates.add(new Candidate("Tcl", 3));
    expectedCandidates.add(new Candidate("TECO", 3));
    expectedCandidates.add(new Candidate("TeX", 3));
    expectedCandidates.add(new Candidate("TEX", 3));
    expectedCandidates.add(new Candidate("TIE", 3));
    expectedCandidates.add(new Candidate("Tom", 3));
    expectedCandidates.add(new Candidate("TOM", 3));
    expectedCandidates.add(new Candidate("TPU", 3));
    expectedCandidates.add(new Candidate("Trac", 3));
    expectedCandidates.add(new Candidate("TTCN", 3));
    expectedCandidates.add(new Candidate("TTM", 3));
    expectedCandidates.add(new Candidate("TUTOR", 3));
    expectedCandidates.add(new Candidate("TXL", 3));
    expectedCandidates.add(new Candidate("Umple", 3));
    expectedCandidates.add(new Candidate("Uniface", 3));
    expectedCandidates.add(new Candidate("UNITY", 3));
    expectedCandidates.add(new Candidate("VBA", 3));
    expectedCandidates.add(new Candidate("VHDL", 3));
    expectedCandidates.add(new Candidate("VSXu", 3));
    expectedCandidates.add(new Candidate("WebQL", 3));
    expectedCandidates.add(new Candidate("X++", 3));
    expectedCandidates.add(new Candidate("X10", 3));
    expectedCandidates.add(new Candidate("XBL", 3));
    expectedCandidates.add(new Candidate("XOTcl", 3));
    expectedCandidates.add(new Candidate("XPL", 3));
    expectedCandidates.add(new Candidate("XPL0", 3));
    expectedCandidates.add(new Candidate("XSB", 3));
    expectedCandidates.add(new Candidate("XSLT", 3));
    expectedCandidates.add(new Candidate("YQL", 3));
    expectedCandidates.add(new Candidate("Zeno", 3));
    expectedCandidates.add(new Candidate("ZOPL", 3));
    expectedCandidates.add(new Candidate("ZPL", 3));
  }

  @DataProvider(name = "serializers")
  public Iterator<Object[]> serializers() {
    final List<Object[]> serializers = new LinkedList<>();
    serializers.add(new Object[] {new BytecodeSerializer()});
    serializers.add(new Object[] {new ProtobufSerializer()});
    return serializers.iterator();
  }

  @Test(dataProvider = "serializers")
  public void testSerialization(final Serializer serializer) throws Exception {
    final byte[] bytes = serializer.serialize(transducer);
    final ITransducer<Candidate> actualTransducer =
      (ITransducer<Candidate>)
        serializer.deserialize(Transducer.class, bytes);
    assertThat(actualTransducer).isEqualTo(transducer);
  }

  @Test
  public void testTransduce() {
    final ICandidateCollection<Candidate> actualCandidates = transducer.transduce(QUERY_TERM);
    final Iterator<Candidate> actualIter = actualCandidates.iterator();

    val distance = new MemoizedMergeAndSplit();

    while (actualIter.hasNext()) {
      final Candidate actualCandidate = actualIter.next();
      assertThat(actualCandidate).hasDistance(distance, QUERY_TERM);
      assertThat(expectedCandidates).contains(actualCandidate);
      expectedCandidates.remove(actualCandidate);
    }

    assertThat(expectedCandidates).isEmpty();
  }
}
