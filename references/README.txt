*NO* warranty expressed or implied: use at your own risk!

Copyright 2006 by Alex Martelli (aleaxit@gmail.com)

This work is licensed under the Creative Commons Attribution 2.5
License (see http://creativecommons.org/licenses/by/2.5/).

The code, being a derivative work of Bo Haglund's DDS 1.0 which is
licensed under GPL, is of necessity also licensed under the GPL.

Bo Haglund's original work is at http://web.telia.com/~u88910365/ .
Alex Marelli's version is at http://www.aleax.it/Bridge/ .

For the most current version of the sources, get dds10.zip (no binaries
are in there).  For a more dated version which does contain some
binaries ready to run, get ddsprogs.zip (and see the notes that follow,
which are obsolete except regarding the contents of ddsprogs.zip and
dds1n2n.txt.bz2 -- Bo has now released his sources under the GPL, so I
can and do distribute a derivative work of those sources in dds10.zip).

I am still actively working on this (mostly the Python-wrapper extension
module, now called pydds) and intend to make available better versions
in the future, but the importance of Bo's work is such that I wanted to
rush to make available versions suitable for non-Windows systems.


Alex Martelli, Palo Alto (CA), May 29 2006


Here follows the rest of the original README.txt, also contained in
ddsprogs.zip:


These programs (where it matters) run on MacOSX 10.4.*, with Universal
Python 2.4.3, on Macintosh machines with either Intel or PowerPC
processors.  Everything herein described is contained in zipfile
ddsprogs.zip unless otherwise noticed.  (To learn more about Python and
frely download the needed version, see http://www.python.org).

The intent is for this zipfile (and directory) to supply all you need
(besides Python and a Mac) to reproduce and extend the research results
presented in file strat1.txt (also present in this zipfile).  As per the
above-mentioned Creative Commons license, you may freely republish my
work and add more materials to it, but you must give me credit and
clearly distinguish your contributions from mine.


Contents of this zipfile and/or the containing directory:

dealer is a fat-binary build of the hand-generation program whose
sources are available at http://www.dombo.org/henk/dealer.html .

ntraise.dds is a controlfile for dealer, to deal flat (4333) hands with
15-17 HCP in North and 8-10 HCP in South.

dds.so is a Mac/Python version of Bo Haglund's 'DDS', available at
http://web.telia.com/~u88910365/ in a pure Windows version, for double
dummy evaluation (sorry, no docs of the Python interfacing available
yet: reverse engineer it from the provided Python sources!).  The
sources from which dds.so was built are the property of Bo Haglund (Alex
Martelli provided only the porting to MacOSX and Python interfacing).
((Special thanks to Bo for making the sources available to me!!!))

dodds.py uses dealer and dds.so to generate and evaluate bridge deals.
In the same directory as this README.txt file (or the zipfile which
contains it), http://www.aleax.it/Bridge/dds1n2n.txt.bz2 is a bzip2
compressed version the logfile dodds.py produced, with 400,000 deals
used for these research studies.  Careful: this file is over 1OMB!

summary.py can read the big logfile (must be bunzip'd first!) and
summarize it into a Python dictionary; a pickled version of that
dictionary is in pick_dds.pick.  analyze.py reads the dictionary from
the .pick file and performs analysis of various kind (currently, you
must edit the sourcefile to determine exactly what analysis it does).


Assuming you don't want to regenerate and double-dummy analyze many
hands (which requires one or more macs, and many hours or days), one
suggested line of exploration is to change summary.py to perform a
different kind of hand evauation (not the HCP's which are given in the
logfile as a convenience), and analyze.py to use the proper ranges and
kinds of strategies for other hand-evaluation techniques.  All of this
requires Python only (as well as bunzip2 for decompressing the logfile)
and thus can run on any machine having Python, be it a Mac or otherwise.

Happy hacking!-)


Alex Martelli, Palo Alto (CA), May 21 2006

