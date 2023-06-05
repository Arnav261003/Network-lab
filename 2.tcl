
set ns [new Simulator]

$ns color 1 Blue
$ns color 2 Red

set nf [open prac2.nam w]
$ns namtrace-all $nf

set nt [open prac2.tr w]
$ns trace-all $nt

proc finish {} {
global ns nf nt
$ns flush-trace
close $nf
close $nt
exec nam prac2.nam &
exit 0
}

set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]

$ns duplex-link $n0 $n2 10Mbps 10ms DropTail
$ns duplex-link $n1 $n2 10Mbps 10ms DropTail
$ns duplex-link $n2 $n3 10Mbps 10ms DropTail
$ns duplex-link $n3 $n4 10Mbps 10ms DropTail
$ns duplex-link $n3 $n5 10Mbps 10ms DropTail


$ns duplex-link-op $n0 $n2 orient down-right
$ns duplex-link-op $n1 $n2 orient up-right
$ns duplex-link-op $n2 $n3 orient right
$ns duplex-link-op $n3 $n4 orient right-up
$ns duplex-link-op $n3 $n5 orient right-down

set tcp [new Agent/TCP]
$tcp set class_ 2
$ns attach-agent $n0 $tcp
set sink [new Agent/TCPSink]
$ns attach-agent $n4 $sink
$ns connect $tcp $sink
$tcp set fid_ 1

set ftp [new Application/FTP]
$ftp attach-agent $tcp
$ftp set packet_size_ 1000
$ftp set rate_ 1mb

set udp [new Agent/UDP]
$ns attach-agent $n1 $udp
set null [new Agent/Null]
$ns attach-agent $n5 $null
$ns connect $udp $null
$udp set fid_ 2

set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set type_ CBR
$cbr set packet_size_ 1000
$cbr set rate_ 1mb

$ns at 1.0 "$ftp start"
$ns at 3.0 "$ftp stop"

$ns at 1.5 "$cbr start"
$ns at 3.5 "$cbr stop"

$ns at 5.0 "finish"

$ns run
